package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

    // VARIABLES

    private Color color;
    private int moveCount;

    // BUILDERS

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    // METHODS

    protected boolean isThereAnOpponentPiece(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().getPieceAt(position);
        return piece != null && piece.getColor() != color;
    }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }

    public void increaseMoveCount() {
        moveCount++;
    }

    public void decreaseMoveCount() {
        moveCount--;
    }

    // GETTERS

    public Color getColor() {
        return color;
    }

    public int getMoveCount() {
        return moveCount;
    }
}
