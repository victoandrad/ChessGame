package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
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
        placeNewPiece(new Rook(board, Color.BLACK), new ChessPosition('a', 8));
        placeNewPiece(new Rook(board, Color.BLACK), new ChessPosition('h', 8));
        placeNewPiece(new Rook(board, Color.WHITE), new ChessPosition('a', 1));
        placeNewPiece(new Rook(board, Color.WHITE), new ChessPosition('h', 1));

        placeNewPiece(new King(board, Color.WHITE), new ChessPosition('e', 1));
        placeNewPiece(new King(board, Color.BLACK), new ChessPosition('d', 8));
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        return (ChessPiece) capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) throw new ChessException("There is no piece on source position");
        if (!board.getPieceAt(position).isThereAnyPossibleMove()) throw new ChessException("There is no possible moves for the chosen piece");
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!getBoard().getPieceAt(source).possibleMove(target)) throw new ChessException("The chosen piece can't move to target position");
    }

    private Piece makeMove(Position source, Position target) {
        Piece piece = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(piece, target);
        return capturedPiece;
    }

    // GETTERS

    public Board getBoard() {
        return board;
    }
}
