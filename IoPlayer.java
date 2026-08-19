// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
import java.io.PrintStream;
import java.util.Scanner;

/**
 * A player class implemented with a Scanner and PrintStream
 * This defaults to System.in and System.out.
 * It's a nice building block if you want to do things in the terminal,
 * or read inputs from a file for repeated games to quickly test the same inputs.
 */
public class IoPlayer implements Player {
    private final Scanner input;
    private final PrintStream output;
    // Whether to print using fancy UTF-8 box characters, or simple characters
    // (I recommend not fancy for files, and fancy for everything else)
    private boolean fancy;
    // if you want to auto accept or reject draw requests, set this,
    // otherwise leave it null,
    // and it will ask the input scanner
    private Boolean autoDraw;
    // If you want to auto accept or reject undo requests, set this,
    // otherwise leave it null,
    // and it will ask the input scanner
    private Boolean autoUndo;

    /**
     * Default constructor using System.In and System.out
     */
    public IoPlayer() {
        this(new Scanner(System.in), System.out);
    }

    /**
     * Bring your own input and output!
     * @param input your input
     * @param output your output
     */
    public IoPlayer(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
        fancy = false;
        // default to asking the user
        autoUndo = null;
        autoDraw = null;
    }

    /**
     * getter method
     * @return current input scanner
     */
    public Scanner getInput() {
        return input;
    }

    /**
     * getter method
     * @return current output printstream
     */
    public PrintStream getOuput() {
        return output;
    }

    /**
     * setter method
     * @param fancy whether you want to use fancy box building chars or simple ones
     */
    public void setFancyFormatting(boolean fancy) {
        this.fancy = fancy;
    }

    /**
     * getter method
     * <p>
     * if you want to auto accept or reject draw requests, set this,
     * otherwise leave it null,
     * and it will ask the input scanner
     * @param autoDraw the new value
     */
    public void setAutoDraw(Boolean autoDraw) {
        this.autoDraw = autoDraw;
    }

    /**
     * getter method
     * <p>
     * if you want to auto accept or reject undo requests, set this,
     * otherwise leave it null,
     * and it will ask the input scanner
     * @param autoUndo the new value
     */
    public void setAutoUndo(Boolean autoUndo) {
        this.autoUndo = autoUndo;
    }

    /**
     * Print out the current position to output
     * @param game your game class
     */
    public void printPos(Game game) {
        char[] position = game.position();
        // fancy version;
        // see https://en.wikipedia.org/wiki/Box-drawing_characters
        String row = fancy ? "━╋━╋━" : "-+-+-";
        char sep = fancy ? '┃' : '|';
        for (int y = 0; y < Game.BOARD_SIZE; y++) {
            if (y > 0) {
                output.println(row);
            }
            for (int x = 0; x < Game.BOARD_SIZE; x++) {
                if (x > 0) output.print(sep);
                output.print(position[Game.BOARD_SIZE * y + x]);
            }
            output.println();
        }
    }

    /**
     * Read in the next action
     * @param game your game class
     */
    @Override
    public int chooseMove(Game game) {
        printPos(game);
        int response;
        System.out.println("Enter a move: [0-8]");
        System.out.printf("Offer a draw [%d]\n", Game.OFFER_DRAW);
        System.out.printf("Resign [%d]\n", Game.RESIGN);
        System.out.printf("Request Undo [%d]\n", Game.REQUEST_UNDO);
        response = input.nextInt();
        while (response < Game.REQUEST_UNDO || response > Game.BOARD_CELLS) {
            System.out.println("Invalid choice. Try again");
            response = input.nextInt();
        }
        return response;
    }

    public void informDraw(Game game) {
        printPos(game);
        output.println("The game has ended in a draw.");
    }

    @Override
    public void informDefeat(Game game) {
        printPos(game);
        output.println("You lost.");
    }

    @Override
    public void informVictory(Game game) {
        printPos(game);
        output.println("You won!");
    }

    /**
     * Helper method to ask the user yes/no questions
     * to avoid some large duplicated code
     * @return true if the user said yes, false if the user said no
     */
    private boolean yesOrNo() {
        do {
            output.print("Accept? (y/n) ");
            // I read the entire line in case they answer "yes" or "no"
            String answer = input.nextLine();
            answer = answer.trim();
            if (answer.charAt(0) == 'y') {
                return true;
            } else if (answer.charAt(0) == 'n') {
                return false;
            }
            output.println("Invalid input! Try again");
        } while (true);
    }

    @Override
    public boolean offerDraw(Game game) {
        if (autoDraw != null) {
            return autoDraw;
        }
        output.println("The other player is offering a draw");
        return yesOrNo();
    }

    @Override
    public boolean requestUndo(Game game) {
        if (autoUndo != null) {
            return autoUndo;
        }
        output.println("The other player is requesting an undo");
        return yesOrNo();
    }
}
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
