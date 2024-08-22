package boardgame;

public class Board {

    // VARIABLES

    private int rows;
    private int columns;
    private Piece[][] pieces;

    // BUILDERS

    public Board(int rows, int columns) {
        if (rows < 1 || columns < 1) throw new BoardException("Board must have at least 1 row and 1 column");
        this.rows = rows;
        this.columns = columns;
        this.pieces = new Piece[rows][columns];
    }

    // METHODS

    public Piece getPieceAt(int row, int column) {
        if (!positionExists(row, column)) throw new BoardException("Position not found");
        return pieces[row][column];
    }

    public Piece getPieceAt(Position position) {
        return getPieceAt(position.getRow(), position.getColumn());
    }

    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) throw new BoardException("There is already a piece in this position");
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    public Piece removePiece(Position position) {
        if (!positionExists(position)) throw new BoardException("Position not found");
        if (getPieceAt(position) == null) return null;
        Piece piece = getPieceAt(position);
        piece.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return piece;
    }

    public boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece(Position position) {
        if (!positionExists(position)) throw new BoardException("Position not found");
        return getPieceAt(position) != null;
    }

    // GETTERS

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
