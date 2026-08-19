// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// The only illegal import here is java.io.File
// I am using it to not give away the bot logic
import java.io.File;
// why would you need this?
import java.util.Scanner;
// all below imports are completely legal
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * A provided Perfect Bot, that reads moves from a file
 * and randomly picks from the best moves available.
 * Due to bad behavior in class, I've upgraded perfect-moves.txt
 * to better-than-perfect-moves.txt.
 * <p>
 * perfect-moves.txt will win if possible,
 * but not effectively prolong the game if losing, or shorten the game if winning
 * <p>
 * better-than-perfect-moves.txt will win if possible (the same positions are winning or losing for both files),
 * but will make the game as long as possible if losing, and as short as possible if winning
 */
public class PerfectBotFile implements Player {
    // There are better data structures we will learn about
    // later in the semester, but this works!
    //
    // This list is sorted by id, so binary search will work
    private static final ArrayList<PerfectMove> movesList;

    /*
     * A static block is ran essentially when the class is first used
     */
    static {
        movesList = new ArrayList<>();
        String filename = "better-than-perfect-moves.txt";

        Scanner s = null;
        try {
            s = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not open " + filename + " needed for PerfectBotFile.\n" +
                    "Please make sure you have the correct folder open in IntelliJ");
        }
        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] values = line.split(" ");
            if (values.length != 3) {
                System.out.println(line);
                throw new AssertionError(filename +
                        " is not being parsed correctly. " +
                        "Check that it is identical to the uploaded file");
            }
            int id = Integer.parseInt(values[0]);
            int moves = Integer.parseInt(values[1]);
            int result = Integer.parseInt(values[2]);
            PerfectMove move = new PerfectMove(id, moves, result);
            movesList.add(move);
        }
        movesList.trimToSize(); // not needed
    }


    /**
     * Just randomly pick one of the optimal moves
     * @param game Your game class
     * @return an optimal move from 0..=8
     */
    @Override
    public int chooseMove(Game game) {
        return RandomBot.random(findMove(game).moveBestOptions());
    }

    /**
     * Use that binary search to find the best move from the lifetimes
     * @param game Your game class
     * @return The selection of best moves, and predicted result
     */
    PerfectMove findMove(Game game) {
        int id = stateId(game.lifetimes());
        int index = binarySearchMove(id, 0, movesList.size() - 1);
        if (index == -1) {
            throw new AssertionError("Wow! You found a state that I didn't.\n" +
                    "Either your game allows for illegal states, or there's a mistake in my code.\n" +
                    "Please send me an email at astryn.strickley@unf.edu" +
                    "and include the id: " + id);
        }
        return movesList.get(index);
    }

    /**
     * Woah it's an algorithm we did in class!
     * @param id the id we're searching for, movesList is sorted by this
     * @param start the start index of the search
     * @param end the end index of the search
     * @return the index of the found item, or -1 if not found
     */
    private int binarySearchMove(int id, int start, int end) {
        if (start > end) {
            return -1;
        }
        int middle = (start + end) / 2;
        PerfectMove middleValue = movesList.get(middle);
        int middleId = middleValue.stateId();
        if (id == middleId) {
            return middle;
        } else if (id < middleId) {
            return binarySearchMove(id, start, middle - 1);
        } else {
            return binarySearchMove(id, middle + 1, end);
        }
    }

    /**
     * Magical function that converts lifetimes into a number to make my file easier parsing/writing the files
     * (Technically you can do everything with these ID's and that how the original PerfectBot works, but I wouldn't recommend it)
     * @param lifetimes the lifetimes array as defined by {@link Game#lifetimes()}
     * @return the magical state id as a number
     */
    public static int stateId(int[] lifetimes) {
        int result = 0;
        for (int i = 8; i >= 0; i--) {
            result <<= 3;
            result |= lifetimes[i];
        }
        return result;
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
    public void informDefeat(Game game) { }
    @Override
    public void informVictory(Game game) { }
    @Override
    public void informDraw(Game game) { }
}
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

class PerfectMove {
    private final int stateId;
    private final int moves;
    // 1 represents a predicted win in the current state
    // 0 represents a predicted draw in the current state
    // -1 represents a predicted loss in the current state
    private final int result;

    public PerfectMove(int stateId, int moves, int result) {
        this.stateId = stateId;
        this.moves = moves;
        this.result = result;
    }

    public int stateId() {
        return stateId;
    }

    public int movesMagic() {
        return moves;
    }

    public int result() {
        return result;
    }

    public ArrayList<Integer> moveBestOptions() {
        ArrayList<Integer> result = new ArrayList<>();
        int curr = moves;
        for (int i = 0; i < 9; i++) {
            if ((curr & 1) == 1) {
                result.add(i);
            }
            curr >>= 1;
        }
        return result;
    }
}
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
