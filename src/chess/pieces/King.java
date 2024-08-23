package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    // VARIABLES

    private ChessMatch chessMatch;

    // BUILDERS

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    // METHODS

    private boolean testRookCastling(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().getPieceAt(position);
        return piece != null && piece instanceof Rook && piece.getColor() == getColor() && piece.getMoveCount() == 0;
    }

    public boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().getPieceAt(position);
        return piece == null || piece.getColor() != getColor();
    }

    @Override
    public boolean[][] getPossibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        // above
        p.setValues(position.getRow() - 1, position.getColumn());
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // below
        p.setValues(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // left
        p.setValues(position.getRow(), position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // left
        p.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // nw
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // ne
        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // sw
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // se
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // castling
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(posT1)) {
                Position pos1 = new Position(position.getRow(), position.getColumn() + 1);
                Position pos2 = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().getPieceAt(pos1) == null && getBoard().getPieceAt(pos2) == null) {
                    mat[position.getRow()][position.getColumn() + 2] = true;
                }
            }
            Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(posT2)) {
                Position pos1 = new Position(position.getRow(), position.getColumn() - 1);
                Position pos2 = new Position(position.getRow(), position.getColumn() - 2);
                Position pos3 = new Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().getPieceAt(pos1) == null && getBoard().getPieceAt(pos2) == null && getBoard().getPieceAt(pos3) == null) {
                    mat[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }

        return mat;
    }

    @Override
    public String toString() {
        return "K";
    }
}
