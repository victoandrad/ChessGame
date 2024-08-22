package boardgame;

public class Position {

    // VARIABLES

    private int row;
    private int column;

    // BUILDERS

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    // METHODS

    public void setValues(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return row + "," + column;
    }

    // GETTERS

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    // SETTERS

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
