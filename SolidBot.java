// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
import java.util.ArrayList;

public class SolidBot extends SimpleBot {


    @Override
    public int chooseMove(Game game) {
        ArrayList<Integer> blankSpots = getBlanks(game);
        Integer winBlock = winOrBlock(game, blankSpots);
        if (winBlock != null) {
            return winBlock;
        }

        // the following logic is intentionally weak
        // I don't want making a bot better than this to be too hard

        final int center = 4;
        if (blankSpots.contains(center)) {
            return center;
        }

        Integer oppCorner = oppositeCorner(blankSpots);
        if (oppCorner != null) {
            return oppCorner;
        }

        return random(blankSpots);
    }

    static Integer oppositeCorner(ArrayList<Integer> blankSpots) {
        int[][] cornerPairs = {{0, 8}, {2, 6}};
        for (int[] pair: cornerPairs) {
            for (int i = 0; i <= 1; i++) {
                if (blankSpots.contains(pair[i]) && !blankSpots.contains(pair[1 - i])) {
                    return pair[i];
                }
            }
        }
        return null;
    }
}
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
