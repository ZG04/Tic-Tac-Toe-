// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * This is the primary interface you need to implement for the project.
 * Create your own file, and return an instance of it in the corresponding Main methods.
 * Your class should have an overall space complexity of O(moveHistoryLimit)
 */
public interface Game {
    // How wide is the board? 3x3
    int BOARD_SIZE = 3;
    // How many cells are there? 3x3 = 9
    int BOARD_CELLS = BOARD_SIZE * BOARD_SIZE;
    // How many symbols can you have on the board if it isn't winning?
    // Hint: if it is winning, you can have MAX_SYMBOLS + 1
    int MAX_SYMBOLS = 6;
    // What is the return value of player chooseMove to offer a draw?
    int OFFER_DRAW = -1;
    // What is the return value of player chooseMove to resign?
    int RESIGN = -2;
    // What is the return value of player chooseMove to request an undo?
    int REQUEST_UNDO = -3;

    /**
     * A provided helper function to let you convert coordinates to an index
     * <p>
     * Board indices:
     * <p>
     * 0 | 1 | 2
     * <p>
     * 3 | 4 | 5
     * <p>
     * 6 | 7 | 8
     * <p>
     * Coordinates:
     * <p>
     * (0, 0) | (1, 0) | (2, 0)
     * <p>
     * (0, 1) | (1, 1) | (2, 1)
     * <p>
     * (0, 2) | (1, 2) | (2, 2)
     * <p>
     * @param x the horizontal position from the left, 0 indexed
     * @param y the vertical position from the top, 0 indexed
     * @return the index of the board, used in lifetimes() and position()
     */
    static int index(int x, int y) {
        if (x < 0 || x > BOARD_SIZE || y < 0 || y > BOARD_SIZE) {
            throw new IllegalArgumentException();
        }
        return BOARD_SIZE * y + x;
    }

    /**
     * Time Complexity: O(1)
     * Space Complexity: O(1) (hint: 9 is a constant)
     * returns an array of how long each cell has left to live
     * Should be values 0-6 (inclusive)
     * 0 represents a dead cell
     * 1 represents a cell that will be removed after the current turn
     * etc.
     * @return array for each cell, read with the following layout
     * <p>
     * result[0] | result[1] | result[2]
     * <p>
     * ----------+-----------+------------
     * <p>
     * result[3] | result[4] | result[5]
     * <p>
     * ----------+-----------+------------
     * <p>
     * result[6] | result[7] | result[8]
     */
    int[] lifetimes();

    /**
     * Time Complexity: O(1)
     * Space Complexity: O(1) (hint: 9 is a constant)
     * A representation of the symbols on the board
     * 'X' is placed by the first player
     * 'O' is placed by the second player
     * ' ' represents an empty space
     * @return array for each cell, read with the following layout
     * <p>
     * result[0] | result[1] | result[2]
     * <p>
     * ----------+-----------+------------
     * <p>
     * result[3] | result[4] | result[5]
     * <p>
     * ----------+-----------+------------
     * <p>
     * result[6] | result[7] | result[8]
     * <p>
     */
    char[] position();

    /**
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     * How many moves have taken place so far?
     * Should start with 0 and increase with each move
     * @return the number of moves so far
     */
    int currentMoveNumber();

    /**
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     * I.e., if the user were to undo,
     * would the correct previous state be in your history and be able to be recalled?
     * <p>
     * Failing to implement undo functionality, or
     * implementing this function as a {@code return false;} will result in a -30 point deduction
     * @return whether is it possible for the current player to undo or not
     */
    boolean canUndo();

    /**
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     * Who's turn is it?
     * 'X' is the first player
     * 'O' is the second player
     * @return either 'X' or 'O'
     */
    char turn();

    /**
     * IMPORTANT:
     * Ignoring calls to chooseMove (not your concern here), all operations in this function
     * should have O(1) time complexity
     * This class overall should have O(historySize) space complexity
     * <p>
     * Start a game from scratch (potentially resetting a completed one)
     * Continuously plays an entire game until completion and returns the winner.
     * If both players agree to a draw, null should be returned
     * <p>
     * To play the game, alternate between players calling {@link Player#chooseMove(Game)},
     * handling all possible responses.
     * <p>
     * The possible return values are the following:
     * <ul>
     * <li> {@link Game#REQUEST_UNDO} </li>
     * <li> {@link Game#RESIGN} </li>
     * <li> {@link Game#OFFER_DRAW} </li>
     * <li> or a move 0..=8 </li>
     * </ul>
     * <hr>
     * <hr>
     * If you receive {@link Game#REQUEST_UNDO} from a player,
     * you should first call {@link Game#canUndo()} to verify that the undo is possible according to you.
     * If not, do act as if the other player rejected the undo.
     * If the undo is possible, you should ask the other player if
     * they accept using {@link Player#requestUndo(Game)}. If they accept,
     * remove the last symbol added by the player the requested undo (two symbols backwards)
     * <p>
     * For example:
     * <p>
     * X | O |
     * <p>
     * O | X |
     * <p>
     * &nbsp | X | O
     * <hr>
     * <p>
     * X | O | X
     * <p>
     * O | &nbsp |
     * <p>
     * &nbsp | X | O
     * <hr>
     * <p>
     * X | O | X
     * <p>
     * O | O |
     * <p>
     * &nbsp | X |
     * <p>
     * It is now X's turn, and they realize they made a mistake on their first move,
     * by requesting an Undo, they are requesting to return to the below position.
     * If rejected, nothing happens to the board, and {@link Player#chooseMove(Game)} is called again
     * <p>
     * X | O |
     * <p>
     * O | X |
     * <p>
     * &nbsp | X | O
     * <p>
     * (X's turn)
     * <hr>
     * <hr>
     * If you receive {@link Game#RESIGN} from a player,
     * you should use {@link Player#informDefeat(Game)} for the resigning player and {@link Player#informVictory(Game)}
     * for the other player
     * <hr>
     * <hr>
     * If you receive {@link Game#OFFER_DRAW} from a player, you should request a draw
     * from the other player using {@link Player#offerDraw(Game)}.
     * If accepted, inform both players using {@link Player#informDraw(Game)}
     * <hr>
     * <hr>
     * When the game is ended,
     * you should use {@link Player#informDefeat(Game)} for the defeated player,
     * and {@link Player#informVictory(Game)} for the winning player
     * See the Player interface for more info
     * @return the winner as 'X' or 'O', or null in the case of a draw
     */
    Character playGame();

    /**
     * Whether the proposed move would be blocking the opponent from making some
     * three in a row on their next turn
     * <p>
     * example:
     * <p>
     * board layout
     * <p>
     * 0 | 1 | 2
     * <p>
     * 3 | 4 | 5
     * <p>
     * 6 | 7 | 8
     * <p>
     * board
     * <p>
     * X | O |
     * <p>
     * X | X |
     * <p>
     * &nbsp | &nbsp | O
     * <p>
     * it would be O's turn
     * <p>
     * isBlockingMove(5) and isBlockingMove(6) should return true
     * all other legal inputs should return false
     * Consider throwing an error for an illegal index
     * @param index the index of the board the player is asking about,
     *        you can't assume this is a blank spot
     * @return is it blocking?
     */
    boolean isBlockingMove(int index);

    /**
     * Whether the proposed move would be making
     * three in a row
     * <p>
     * example:
     * <p>
     * board layout
     * <p>
     * 0 | 1 | 2
     * <p>
     * 3 | 4 | 5
     * <p>
     * 6 | 7 | 8
     * <p>
     * board
     * <p>
     * X | O |
     * <p>
     * X | X |
     * <p>
     * &nbsp | O | O
     * <p>
     * it would be X's turn
     * <p>
     * isWinningMove(5) and isWinningMove(6) should return true
     * <p>
     * all other legal inputs should return false
     * <p>
     * Consider throwing an error for an illegal index
     * @param index the index of the board the player is asking about,
     *        you can't assume this is a blank spot
     * @return are ya winning?
     */
    boolean isWinningMove(int index);
}
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
