package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {

    // VARIABLES

    private Board board;
    private int turn;
    private Color currentPlayer;
    private List<ChessPiece> piecesOnTheBoard = new ArrayList<>();
    private List<ChessPiece> capturedPieces = new ArrayList<>();

    // BUILDERS

    public ChessMatch() {
        this.board = new Board(8, 8);
        this.turn = 1;
        this.currentPlayer = Color.WHITE;
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
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup() {
        placeNewPiece(new Rook(board, Color.BLACK), new ChessPosition('a', 8));
        placeNewPiece(new Rook(board, Color.BLACK), new ChessPosition('h', 8));
        placeNewPiece(new Rook(board, Color.WHITE), new ChessPosition('a', 1));
        placeNewPiece(new Rook(board, Color.WHITE), new ChessPosition('h', 1));

        placeNewPiece(new King(board, Color.WHITE), new ChessPosition('e', 1));
        placeNewPiece(new King(board, Color.BLACK), new ChessPosition('d', 8));
    }

    public boolean[][] possibleMoves(ChessPosition source) {
        Position position = source.toPosition();
        validateSourcePosition(position);
        return board.getPieceAt(position).getPossibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) throw new ChessException("There is no piece on source position");
        if (((ChessPiece) board.getPieceAt(position)).getColor() != currentPlayer) throw new ChessException("The chosen piece is not yours");
        if (!board.getPieceAt(position).isThereAnyPossibleMove()) throw new ChessException("There is no possible moves for the chosen piece");
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!getBoard().getPieceAt(source).possibleMove(target)) throw new ChessException("The chosen piece can't move to target position");
    }

    private Piece makeMove(Position source, Position target) {
        Piece piece = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(piece, target);

        if (capturedPiece != null) {
            capturedPieces.add((ChessPiece) capturedPiece);
            piecesOnTheBoard.remove(capturedPiece);
        }

        return capturedPiece;
    }

    private void nextTurn() {
        this.turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    // GETTERS

    public Board getBoard() {
        return board;
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public List<ChessPiece> getPiecesOnTheBoard() {
        return piecesOnTheBoard;
    }

    public List<ChessPiece> getCapturedPieces() {
        return capturedPieces;
    }
}
