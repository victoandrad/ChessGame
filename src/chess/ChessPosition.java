package chess;

import boardgame.Position;

public class ChessPosition {

    // VARIABLES

    private char column;
    private int row;

    // BUILDERS

    public ChessPosition(char column, int row) {
        if (column < 'a' | column > 'h' || row < 1 | row > 8) throw new ChessException("ChessPosition values must be between a1 and h8");
        this.column = column;
        this.row = row;
    }

    // METHODS

    public Position toPosition() {
        return new Position(8 - row, column - 'a');
    }

    public static ChessPosition fromPosition(Position position) {
        return new ChessPosition((char)('a' + position.getColumn()), 8 - position.getRow());
    }

    @Override

    public String toString() {
        return " " + column + " " + row;
    }

    // GETTERS

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
