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
    public boolean[][] getPossibleMoves() {
        return new boolean[getBoard().getRows()][getBoard().getColumns()];
    }

    @Override
    public String toString() {
        return "K";
    }
}
