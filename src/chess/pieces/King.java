package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    // BUILDERS

    public King(Board board, Color color) {
        super(board, color);
    }

    // METHODS

    @Override
    public String toString() {
        return "K";
    }
}
