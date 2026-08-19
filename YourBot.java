public class YourBot implements Player {

    @Override
    public int chooseMove(Game game) {

        char[] board = game.position();
        char current = game.turn();
        char enemy;

        if (current == 'X') {

            enemy = 'O';

        } else {

            enemy = 'X';

        }

        for (int i = 0; i < 9; i++) {

            if (board[i] == ' ' && game.isWinningMove(i)) {

                return i;

            }
        }

        for (int i = 0; i < 9; i++) {

            if (board[i] == ' ' && game.isBlockingMove(i)) {

                return i;

            }
        }

        if (board[4] == ' ') {

            return 4;

        }

        if (board[4] == enemy) {

            int move = pickFirstOpen(board, new int[]{0, 2, 6, 8});

            if (move != -1) {

                return move;

            }
        }

        for (int c : new int[]{0, 2, 6, 8}) {

            if (board[c] == current) {

                int nextTo = border(c, board);

                if (nextTo != -1) {

                    return nextTo;

                }
            }
        }

        if (board[0] == current && board[8] == ' ') {

            return 8;

        }

        if (board[8] == current && board[0] == ' ') {

            return 0;

        }

        if (board[2] == current && board[6] == ' ') {

            return 6;

        }

        if (board[6] == current && board[2] == ' ') {

            return 2;

        }

        if (board[4] != ' ' && board[4] != current) {

            int side = pickFirstOpen(board, new int[]{1, 3, 5, 7});

            if (side != -1) {

                return side;

            }
        }

        int corner = pickFirstOpen(board, new int[]{0, 2, 6, 8});

        if (corner != -1) {

            return corner;

        }

        for (int i = 0; i < 9; i++) {

            if (board[i] == ' ') {

                return i;

            }
        }

        return Game.RESIGN;
    }

    private int border(int corner, char[] board) {

        int[][] nextTo = {{1, 3}, {1, 5}, {3, 7}, {5, 7}};
        int[] corners = {0, 2, 6, 8};

        for (int i = 0; i < corners.length; i++) {

            if (corner == corners[i]) {

                for (int side : nextTo[i]) {

                    if (board[side] == ' ') {

                        return side;

                    }
                }
            }
        }

        return -1;
    }

    private int pickFirstOpen(char[] board, int[] positions) {

        for (int i : positions) {

            if (board[i] == ' ') {

                return i;

            }
        }

        return -1;
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

    @Override
    public boolean offerDraw(Game game) {

        return false;

    }

    @Override
    public boolean requestUndo(Game game) {

        return true;

    }
}