package boardgame;

public abstract class Piece {

    // VARIABLES

    protected Position position;
    private Board board;

    // BUILDERS

    public Piece(Board board) {
        this.position = null;
        this.board = board;
    }

    // METHODS

    public abstract boolean[][] getPossibleMoves();

    public boolean possibleMove(Position position) {
        return getPossibleMoves()[position.getRow()][position.getColumn()];
    }

    public boolean isThereAnyPossibleMove() {
        boolean[][] mat = getPossibleMoves();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j]) return true;
            }
        }
        return false;
    }

    // GETTERS

    protected Board getBoard() {
        return board;
    }
}
