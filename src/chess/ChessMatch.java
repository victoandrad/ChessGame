package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    // VARIABLES

    private Board board;
    private int turn;
    private Color currentPlayer;
    private List<ChessPiece> piecesOnTheBoard = new ArrayList<>();
    private List<ChessPiece> capturedPieces = new ArrayList<>();
    private boolean check;
    private boolean checkMate;

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
        placeNewPiece(new Pawn(board, Color.BLACK), new ChessPosition('a', 7));
        placeNewPiece(new Pawn(board, Color.BLACK), new ChessPosition('b', 7));
        placeNewPiece(new Pawn(board, Color.BLACK), new ChessPosition('c', 7));
        placeNewPiece(new Pawn(board, Color.BLACK), new ChessPosition('d', 7));
        placeNewPiece(new Pawn(board, Color.BLACK), new ChessPosition('e', 7));
        placeNewPiece(new Pawn(board, Color.BLACK), new ChessPosition('f', 7));
        placeNewPiece(new Pawn(board, Color.BLACK), new ChessPosition('g', 7));
        placeNewPiece(new Pawn(board, Color.BLACK), new ChessPosition('h', 7));
        placeNewPiece(new Rook(board, Color.BLACK), new ChessPosition('a', 8));
        placeNewPiece(new Rook(board, Color.BLACK), new ChessPosition('h', 8));
        placeNewPiece(new Bishop(board, Color.BLACK), new ChessPosition('c', 8));
        placeNewPiece(new Bishop(board, Color.BLACK), new ChessPosition('f', 8));
        placeNewPiece(new Knight(board, Color.BLACK), new ChessPosition('b', 8));
        placeNewPiece(new Knight(board, Color.BLACK), new ChessPosition('g', 8));
        placeNewPiece(new King(board, Color.BLACK), new ChessPosition('e', 8));
        placeNewPiece(new Queen(board, Color.BLACK), new ChessPosition('d', 8));

        placeNewPiece(new Pawn(board, Color.WHITE), new ChessPosition('a', 2));
        placeNewPiece(new Pawn(board, Color.WHITE), new ChessPosition('b', 2));
        placeNewPiece(new Pawn(board, Color.WHITE), new ChessPosition('c', 2));
        placeNewPiece(new Pawn(board, Color.WHITE), new ChessPosition('d', 2));
        placeNewPiece(new Pawn(board, Color.WHITE), new ChessPosition('e', 2));
        placeNewPiece(new Pawn(board, Color.WHITE), new ChessPosition('f', 2));
        placeNewPiece(new Pawn(board, Color.WHITE), new ChessPosition('g', 2));
        placeNewPiece(new Pawn(board, Color.WHITE), new ChessPosition('h', 2));
        placeNewPiece(new Rook(board, Color.WHITE), new ChessPosition('a', 1));
        placeNewPiece(new Rook(board, Color.WHITE), new ChessPosition('h', 1));
        placeNewPiece(new Bishop(board, Color.WHITE), new ChessPosition('c', 1));
        placeNewPiece(new Bishop(board, Color.WHITE), new ChessPosition('f', 1));
        placeNewPiece(new Knight(board, Color.WHITE), new ChessPosition('b', 1));
        placeNewPiece(new Knight(board, Color.WHITE), new ChessPosition('g', 1));
        placeNewPiece(new King(board, Color.WHITE), new ChessPosition('d', 1));
        placeNewPiece(new Queen(board, Color.WHITE), new ChessPosition('e', 1));
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
        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }
        check = (testCheck(opponent(currentPlayer)));
        if (testCheck(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }
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
        ChessPiece piece = (ChessPiece) board.removePiece(source);
        piece.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(piece, target);

        if (capturedPiece != null) {
            capturedPieces.add((ChessPiece) capturedPiece);
            piecesOnTheBoard.remove(capturedPiece);
        }
        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece piece = (ChessPiece) board.removePiece(target);
        piece.decreaseMoveCount();
        board.placePiece(piece, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add((ChessPiece) capturedPiece);
        }
    }

    private void nextTurn() {
        this.turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece piece : list) {
            if (piece instanceof King) {
                return (ChessPiece) piece;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece piece : opponentPieces) {
            boolean[][] mat = piece.getPossibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    public boolean testCheckMate(Color color) {
        if (!testCheck(color)) return false;
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece piece : list) {
            boolean[][] mat = piece.getPossibleMoves();
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[i].length; j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece)piece).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
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

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }
}
