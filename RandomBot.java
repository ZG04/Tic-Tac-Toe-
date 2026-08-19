// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
import java.util.ArrayList;
import java.util.Random;

public class RandomBot implements Player {
    @Override
    public int chooseMove(Game game) {
        ArrayList<Integer> blankSpots = getBlanks(game);
        return random(blankSpots);
    }

    static int random(ArrayList<Integer> blankSpots) {
        int blankSpotsIndex = new Random().nextInt(blankSpots.size());
        return blankSpots.get(blankSpotsIndex);
    }

    static ArrayList<Integer> getBlanks(Game game) {
        char[] pos = game.position();
        ArrayList<Integer> blankSpots = new ArrayList<Integer>();
        for (int i = 0; i < pos.length; i++) {
            if (pos[i] == ' ') {
                blankSpots.add(i);
            }
        }
        return blankSpots;
    }

    @Override
    public boolean offerDraw(Game game) {
        return false;
    }

    @Override
    public boolean requestUndo(Game game) {
        return true;
    }

    @Override
    public void informDefeat(Game game) {
    }

    @Override
    public void informVictory(Game game) {
    }

    @Override
    public void informDraw(Game game) {
    }
}
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
