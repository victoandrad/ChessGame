package boardgame;

public class Piece {

    // VARIABLES

    protected Position position;
    private Board board;

    // BUILDERS

    public Piece(Board board) {
        this.position = null;
        this.board = board;
    }

    // GETTERS

    protected Board getBoard() {
        return board;
    }
}
