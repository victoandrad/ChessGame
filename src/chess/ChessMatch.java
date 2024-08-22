package chess;

import boardgame.Board;

public class ChessMatch {

    // VARIABLES

    private Board board;

    // BUILDERS

    public ChessMatch() {
        this.board = new Board(8, 8);
    }

    // METHODS

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                mat[i][j] = (ChessPiece) board.getPieceAt(i, j);
            }
        }
        return mat;
    }

    // GETTERS

    public Board getBoard() {
        return board;
    }
}
