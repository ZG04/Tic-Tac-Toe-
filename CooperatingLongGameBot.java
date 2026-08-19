// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
public class CooperatingLongGameBot implements Player {
    private static final int[] moveOrder = {0, 2, 1, 5, 3, 6, 4, 7};

    /**
     * uses the following drawn position concept in a loop
     * <p>
     * X | X | O
     * <p>
     * X | X | O
     * <p>
     * O | O |
     * @param game The current game state
     * @return The selected move. A bot will never request a draw, undo, or resign
     */
    @Override
    public int chooseMove(Game game) {
        int moveNumber = game.currentMoveNumber();
        int index = moveNumber % moveOrder.length;
        int move = moveOrder[index];
        if (game.position()[move] != ' ') {
            throw new AssertionError("This bot is not playing with a copy of itself!");
        }
        return move;
    }

    @Override
    public void informDefeat(Game game) { }

    @Override
    public void informVictory(Game game) { }

    @Override
    public boolean offerDraw(Game game) { return false; }

    @Override
    public boolean requestUndo(Game game) { return true; }

    @Override
    public void informDraw(Game game) { }
}
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
