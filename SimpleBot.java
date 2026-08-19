// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
import java.util.ArrayList;

public class SimpleBot extends RandomBot {

    @Override
    public int chooseMove(Game game) {
        ArrayList<Integer> blankSpots = getBlanks(game);
        Integer winBlock = winOrBlock(game, blankSpots);
        if (winBlock != null) {
            return winBlock;
        }

        return random(blankSpots);
    }

    static public Integer winOrBlock(Game game) {
        return winOrBlock(game, getBlanks(game));
    }

    static public Integer winOrBlock(Game game, ArrayList<Integer> blankSpots) {
        for (int move: blankSpots) {
            if (game.isWinningMove(move)) {
                return move;
            }
        }

        for (int move: blankSpots) {
            if (game.isBlockingMove(move)) {
                return move;
            }
        }

        return null;
    }
}
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
