package boardgame;

public class Board {

    // VARIABLES

    private int rows;
    private int columns;
    private Piece[][] pieces;

    // BUILDERS

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.pieces = new Piece[rows][columns];
    }

    // METHODS

    public Piece getPieceAt(int row, int column) {
        return pieces[row][column];
    }

    public Piece getPieceAt(Position position) {
        return getPieceAt(position.getRow(), position.getColumn());
    }

    public void placePiece(Piece piece, Position position) {
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    // GETTERS

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    // SETTERS

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
}
