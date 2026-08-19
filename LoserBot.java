// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
import java.util.ArrayList;

public class LoserBot extends RandomBot {

    // Will refuse to play winning moves unless forced to
    @Override
    public int chooseMove(Game game) {
        ArrayList<Integer> blankSpots = getBlanks(game);

        for (int i = blankSpots.size() - 1; i >= 0; i--) {
            int spot = blankSpots.get(i);
            if (game.isWinningMove(spot)) {
                blankSpots.remove(i);
            }
        }

        if (blankSpots.isEmpty()) {
            return super.chooseMove(game);
        }

        return random(blankSpots);
    }
}
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
