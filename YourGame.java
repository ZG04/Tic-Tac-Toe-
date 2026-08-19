public class YourGame implements Game  {

    private Player player1;
    private Player player2;
    private int[] movesLeft;
    private char turn;
    private char[] board;// 'X' or 'O'
    private int moveNumber;
    private boolean isOver;
    private int[] moveHistory;
    private int historyLimit;
    private int historySize;
    private int[] placedOrder;
    private int[] removedHistory;
    private int symbolCount;
    private char[] removedSymbolHistory;

    public YourGame(Player x, Player o, int moveHistoryLimit) {

        player1 = x;
        player2 = o;
        historyLimit = moveHistoryLimit;

        board = new char[BOARD_CELLS];
        movesLeft = new int[BOARD_CELLS];
        moveHistory = new int[moveHistoryLimit];
        removedHistory = new int[moveHistoryLimit];
        removedSymbolHistory = new char[moveHistoryLimit];
        placedOrder = new int[BOARD_CELLS];
        clearBoard();

    }

    @Override
    public Character playGame() {

        clearBoard();

        Player current = player1;
        Player enemy = player2;

        while (!isOver) {

            int move = current.chooseMove(this);
            String action;

            if (move == RESIGN) {

                action = "resign";

            } else if (move == OFFER_DRAW) {

                action = "draw";

            } else if (move == REQUEST_UNDO) {

                action = "undo";

            } else {

                action = "move";

            }

            if (action.equals("resign")) {

                current.informDefeat(this);
                enemy.informVictory(this);

                if (turn == 'X') {

                    System.out.println("Resigned Winner is O ");
                    return 'O';

                } else {

                    System.out.println("Resigned Winner is X ");
                    return 'X';

                }

            }


            if (action.equals("draw")) {

                if (enemy.offerDraw(this)) {

                    current.informDraw(this);
                    enemy.informDraw(this);

                    System.out.println("Draw!");
                    return 'D';

                }

                continue;
            }

            if (action.equals("undo")) {
                if (canUndo() && enemy.requestUndo(this)) {

                    undoTwoMoves();
                    continue;

                }

                continue;
            }

            if (action.equals("move")) {

                if (move >= 0 && move < BOARD_CELLS && board[move] == ' ') {

                    placeSymbol(move);

                    if (checkWin(turn)) {

                        current.informVictory(this);
                        enemy.informDefeat(this);

                        System.out.println("Winner: " + turn);
                        return turn;

                    }

                    if (boardFull()) {

                        current.informDraw(this);
                        enemy.informDraw(this);

                        System.out.println("Board full draw");
                        return 'D';
                    }

                    moveNumber++;
                    switchTurn();

                    Player temp = current;
                    current = enemy;
                    enemy = temp;

                }
            }
        }

        isOver = true;
        return null;
    }

    private void clearBoard() {

        for (int i = 0; i < BOARD_CELLS; i++) {

            board[i] = ' ';
            movesLeft[i] = 0;

        }

        moveNumber = 0;
        turn = 'X';
        isOver = false;
        historySize = 0;
        symbolCount = 0;

    }

    private void placeSymbol(int index) {

        board[index] = turn;

        if (historySize >= historyLimit) {

            historySize = historyLimit - 1;

        }

        moveHistory[historySize] = index;
        removedHistory[historySize] = -1;
        removedSymbolHistory[historySize] = ' ';

        if (symbolCount < BOARD_CELLS) {

            placedOrder[symbolCount] = index;
            symbolCount++;

        } else {

            for (int i = 1; i < BOARD_CELLS; i++) {

                placedOrder[i - 1] = placedOrder[i];
                placedOrder[BOARD_CELLS - 1] = index;

            }

        }

        updateLifetimes(true);

        int numSym = countSymbols();

        if (numSym <= MAX_SYMBOLS) {

            historySize++;
            return;

        }

        if (checkWin(turn)) {

            historySize++;
            return;

        }

        int oldest = placedOrder[0];

        for (int i = 1; i < symbolCount; i++) {

            placedOrder[i - 1] = placedOrder[i];

        }

        symbolCount--;

        removedHistory[historySize] = oldest;
        removedSymbolHistory[historySize] = board[oldest];
        board[oldest] = ' ';

        updateLifetimes(false);
        historySize++;

    }

    private void undoTwoMoves() {

        if (historySize == 0) {

            return;

        }

        int movesUndone = 0;

        while (movesUndone < 2 && historySize > 0) {

            historySize--;

            int current = moveHistory[historySize];

            if (current >= 0 && current < BOARD_CELLS) {

                board[current] = ' ';

            }

            int removedSpot = removedHistory[historySize];

            if (removedSpot >= 0 && removedSpot < BOARD_CELLS) {

                board[removedSpot] = removedSymbolHistory[historySize];

            }

            movesUndone++;
        }

        int squaresOccupied = 0;

        for (int i = 0; i < BOARD_CELLS; i++) {

            if (board[i] != ' ') {

                placedOrder[squaresOccupied] = i;
                squaresOccupied++;

            }
        }

        symbolCount = squaresOccupied;

        for (int i = 0; i < BOARD_CELLS; i++) {

            if (board[i] != ' ' && movesLeft[i] > 0) {

                movesLeft[i]--;

            }
        }

        isOver = false;

        moveNumber -= 2;

        if (moveNumber < 0) {

            moveNumber = 0;

        }
    }

    private void updateLifetimes(boolean ageOfPiece) {

        if (ageOfPiece) {

            for (int i = 0; i < BOARD_CELLS; i++) {

                if (board[i] != ' ' && movesLeft[i] > 0) {

                    movesLeft[i]--;

                }
            }
        }

        if (symbolCount == 0) {

            return;

        }

        int newestIndex = placedOrder[symbolCount - 1];

        if (newestIndex >= 0 && newestIndex < BOARD_CELLS) {

            movesLeft[newestIndex] = MAX_SYMBOLS;

        }

        if (checkWin(turn)) {

            return;

        }

        for (int i = 0; i < BOARD_CELLS; i++) {

            if (movesLeft[i] <= 0 && board[i] != ' ') {

                board[i] = ' ';

            }
        }
    }

    @Override
    public int[] lifetimes() {

        int[] copy = new int[BOARD_CELLS];

        for (int i = 0; i < BOARD_CELLS; i++) {

            copy[i] = movesLeft[i];

        }

        return copy;
    }

    @Override
    public char[] position() {

        char[] copy = new char[BOARD_CELLS];

        for (int i = 0; i < BOARD_CELLS; i++) {

            copy[i] = board[i];

        }

        return copy;
    }

    @Override
    public int currentMoveNumber() {

        return moveNumber;

    }

    @Override
    public boolean canUndo() {

        return historySize >= 1;

    }

    @Override
    public char turn() {

        return turn;

    }

    private void switchTurn() {

        if (turn == 'X') {

            turn = 'O';

        }
        else {

            turn = 'X';

        }
    }

    private boolean boardFull() {

        for (int i = 0; i < BOARD_CELLS; i++) {

            if (board[i] == ' ') {

                return false;

            }
        }

        return true;
    }

    private int countSymbols() {

        int count = 0;

        for (int i = 0; i < BOARD_CELLS; i++) {

            if (board[i] != ' ') {

                count++;

            }
        }

        return count;
    }

    private boolean checkWin(char player) {

        int[][] wins = { {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6} };

        for (int i = 0; i < wins.length; i++) {

            int a = wins[i][0];
            int b = wins[i][1];
            int c = wins[i][2];

            if (board[a] == player && board[b] == player && board[c] == player) {

                return true;

            }
        }

        return false;
    }

    @Override
    public boolean isBlockingMove(int index) {

        if (index < 0 || index >= BOARD_CELLS) {

            throw new IllegalArgumentException();

        }

        if (board[index] != ' ') {

            return false;

        }

        char enemy;

        if (turn == 'X') {

            enemy = 'O';

        } else {

            enemy = 'X';

        }

        board[index] = enemy;
        boolean blocks = checkWin(enemy);
        board[index] = ' ';
        return blocks;

    }

    @Override
    public boolean isWinningMove(int index) {

        if (index < 0 || index >= BOARD_CELLS) {

            throw new IllegalArgumentException();

        }

        if (board[index] != ' ') {

            return false;

        }

        board[index] = turn;
        boolean wins = checkWin(turn);
        board[index] = ' ';
        return wins;

    }
}