// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
public interface Player {
    /**
     * Ask the player to choose a move,
     * to resign,
     * request a draw,
     * or request an undo
     * @param game The current game state
     * @return {@link Game#REQUEST_UNDO}, {@link Game#RESIGN}, {@link Game#OFFER_DRAW}, or a move 0..=8
     * @see Game#playGame() for more info
     */
    public int chooseMove(Game game);

    /**
     * Called by the game class to inform you of your defeat
     * <p>
     * Bots should ignore this function and do nothing
     * @param game The current game state
     */
    public void informDefeat(Game game);

    /**
     * Called by the game class to inform you of your victory
     * <p>
     * Bots should ignore this function and do nothing
     * @param game The current game state
     */
    public void informVictory(Game game);

    /**
     * Called by the game class to inform you of your agreed draw
     * <p>
     * Bots should ignore this function and do nothing
     * @param game The current game state
     */
    public void informDraw(Game game);

    /**
     * Called by the game class when the other player wants a draw
     * <p>
     * Bots should always reject draws
     * @param game The current game state
     * @return true if you accept, false if you decline
     */
    public boolean offerDraw(Game game);

    /**
     * Called by the game class when the other player wants an undo
     * <p>
     * Bots should always accept undos
     * @param game The current game state
     * @return true if you accept, false if you decline
     */
    public boolean requestUndo(Game game);

}
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
