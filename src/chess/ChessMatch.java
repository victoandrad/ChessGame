package chess;

import boardgame.Board;
import chess.ChessPosition;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

    // VARIABLES

    private Board board;

    // BUILDERS

    public ChessMatch() {
        this.board = new Board(8, 8);
        initialSetup();
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

    public void placeNewPiece(ChessPiece piece, ChessPosition position) {
        board.placePiece(piece, position.toPosition());
    }

    private void initialSetup() {
        placeNewPiece(new Rook(board, Color.WHITE), new ChessPosition('a', 8));
        placeNewPiece(new Rook(board, Color.WHITE), new ChessPosition('h', 8));
        placeNewPiece(new Rook(board, Color.WHITE), new ChessPosition('a', 1));
        placeNewPiece(new Rook(board, Color.WHITE), new ChessPosition('h', 8));

        placeNewPiece(new King(board, Color.BLACK), new ChessPosition('e', 1));
        placeNewPiece(new King(board, Color.BLACK), new ChessPosition('d', 8));
    }

    // GETTERS

    public Board getBoard() {
        return board;
    }
}
