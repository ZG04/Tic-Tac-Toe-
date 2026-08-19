// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This is a provided class to provide a graphical user interface
 * as a Player.
 * It provides a way to visualize the remaining lifetime of each symbol
 * as well as a way to enable hints, which display the empty squares that
 * are either blocking or winning moves according to your game class.
 */
public class GuiPlayer implements Player {
    private static final int FONT_SIZE = 40;

    private final JFrame window;
    private final JButton[] buttons;
    private final JButton undoButton;
    private final JButton drawButton;
    private final JButton resignButton;

    private boolean hintsEnabled;

    // This is a really bad hack to get this to work
    // with the simple interface
    // a gui really should never wait for user input like this,
    // but this api keeps other Player implementations simple
    // so the extra credit is more reasonable,
    // rather than a proper  never wait for user input like this,
    // but this api keeps other Player implementations simple
    // so the extra credit is more reasonable,
    // rather than a proper async api.
    private Integer userSelection;
    // yeah... this really shouldn't be necessary, and it isn't
    private static final long SLEEP_TIME_MS = 200;

    /**
     * Big Ugly Constructor
     * <p>
     * It looks like a lot of code, but it's really just setting up the GUI
     */
    public GuiPlayer() {
        // Honestly, don't take this as good UI design
        // I'm very rusty at Swing, it's an old library,
        // and I'm not very good at making good UI
        // If you'd like to share improvements, feel free to reach out
        window = new JFrame("Tic-Tac-Toe Player");
        window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));

        JPanel gameGrid = new JPanel(new GridLayout(Game.BOARD_SIZE, Game.BOARD_SIZE));
        gameGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttons = new JButton[Game.BOARD_CELLS];

        for (int i = 0; i < Game.BOARD_CELLS; i++) {
            JButton button = new JButton();
            button.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
            button.setEnabled(false);
            button.setMinimumSize(new Dimension(100, 100));
            button.setText(" ");
            button.addActionListener(new ButtonPressedUpdate(i));
            gameGrid.add(button);
            buttons[i] = button;
        }

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEADING));
        undoButton = new JButton();
        undoButton.setEnabled(false);
        undoButton.setText("Undo");
        undoButton.addActionListener(new ButtonPressedUpdate(Game.REQUEST_UNDO));
        buttonRow.add(undoButton);

        drawButton = new JButton();
        drawButton.setEnabled(false);
        drawButton.setText("Offer Draw");
        drawButton.addActionListener(new ButtonPressedUpdate(Game.OFFER_DRAW));
        buttonRow.add(drawButton);

        resignButton = new JButton();
        resignButton.setEnabled(false);
        resignButton.setText("Resign");
        resignButton.addActionListener(new ButtonPressedUpdate(Game.RESIGN));
        buttonRow.add(resignButton);

        buttonRow.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        window.add(buttonRow);
        window.add(gameGrid);


        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        window.setSize(300, 350);
        window.setVisible(true);

        userSelection = null;
        hintsEnabled = false;
    }

    /**
     * Update the state of the game board.
     * <p>
     * Used when the game is requesting user input, or just wants to update the display
     * (such as at the end of the game)
     * @param game your game class
     * @param enableButtons whether the buttons are clickable or not
     */
    private void updateBoard(Game game, boolean enableButtons) {
        if (enableButtons) {
            drawButton.setEnabled(true);
            resignButton.setEnabled(true);
            undoButton.setEnabled(game.canUndo());
        }
        char[] position = game.position();
        int[] lifetimes = game.lifetimes();
        for (int i = 0; i < Game.BOARD_CELLS; i++) {
            JButton button = buttons[i];
            boolean positionSet = position[i] != ' ';
            if (positionSet) {
                String text = String.format("<html>%c<sub>%d</sub></html>", position[i], lifetimes[i]);
                button.setText(text);
                button.setEnabled(false);
                button.setBackground(null);
            }
            else {
                button.setText(" ");
                if (hintsEnabled) {
                    if (game.isWinningMove(i)) {
                        button.setBackground(Color.GREEN);
                    } else if (game.isBlockingMove(i)) {
                        button.setBackground(Color.YELLOW);
                    } else {
                        button.setBackground(null);
                    }
                }
                button.setEnabled(enableButtons);
            }
        }
    }

    /**
     * Useful for having multiple games as long as the window is open
     * @return true if the window is still open
     */
    public boolean playerAvailable() {
        return window.isDisplayable();
    }

    /**
     * Enable or disable hints.
     * Hints display the empty squares that
     * are either blocking or winning moves according to your game class.
     * <p>
     * This currently defaults to false, so you can make the {@link Game#position()} and
     * {@link Game#lifetimes()} methods before the more complex {@link Game#isWinningMove(int)}
     * and {@link Game#isBlockingMove(int)} in your Game implementation.
     * @param hintsEnabled whether to enable hints (or disable)
     */
    public void setHintsEnabled(boolean hintsEnabled) {
        this.hintsEnabled = hintsEnabled;
    }

    /**
     * Uses some awful code to wait for the user to choose their square
     * @param game Your game instance
     * @return {@link Game#REQUEST_UNDO}, {@link Game#RESIGN}, {@link Game#OFFER_DRAW}, or a move 0..=8
     */
    @Override
    public int chooseMove(Game game) {
        window.setTitle(String.format("Your turn (%c)!", game.turn()));
        userSelection = null;
        updateBoard(game, true);
        // yeah... this is awful
        // please never do this in real code
        // again, the point is to make life easier
        // for the Players that are not GUIs
        while (userSelection == null) {
            try {
                if (!playerAvailable()) {
                    return Game.RESIGN;
                }
                //noinspection BusyWait
                Thread.sleep(SLEEP_TIME_MS);
            } catch (InterruptedException e) {
                window.setVisible(false);
                window.dispose();
            }
        }
        char turn = game.turn();
        if (0 <= userSelection && userSelection < Game.BOARD_CELLS) {
            buttons[userSelection].setText(String.valueOf(turn));
        }
        disableButtons();
        window.setTitle("Waiting on opponent...");
        return userSelection;
    }

    /**
     * Tell the user something! And update the board!
     * @param game your game class
     * @param message what you want to say!
     */
    private void informUser(Game game, String message) {
        if (!playerAvailable()) {
            return;
        }
        updateBoard(game, false);
        window.setTitle(message);
        JOptionPane.showMessageDialog(window, message);
    }

    @Override
    public void informDefeat(Game game) {
        informUser(game, "You lost!");
    }

    @Override
    public void informVictory(Game game) {
        informUser(game, "You won!");
    }

    @Override
    public void informDraw(Game game) {
        informUser(game, "The game has ended in a draw.");
    }

    @Override
    public boolean offerDraw(Game game) {
        return 0 == JOptionPane.showConfirmDialog(
                window,
                "The other player is offering a draw. Accept?",
                "Draw Offer",
                JOptionPane.YES_NO_OPTION);
    }

    @Override
    public boolean requestUndo(Game game) {
        return 0 == JOptionPane.showConfirmDialog(
                window,
                "The other player is requesting and undo. Accept?",
                "Undo Request",
                JOptionPane.YES_NO_OPTION);
    }

    /**
     * turn all the buttons off!
     */
    public void disableButtons() {
        undoButton.setEnabled(false);
        resignButton.setEnabled(false);
        drawButton.setEnabled(false);
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    /**
     * A custom class to handle the button click events.
     * It's a pretty awful hack like the rest of the code in this file.
     */
    class ButtonPressedUpdate implements ActionListener{
        private int i;

        public ButtonPressedUpdate(int index) {
            i = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // yet another part of this bad hack
            // pressing the button should just directly update the game
            userSelection = i;
        }
    }
}
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
