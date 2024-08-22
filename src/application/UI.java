package application;

import chess.ChessPiece;

public abstract class UI {

    public static void printBoard(ChessPiece[][] board) {
         for (int i = 0; i < board.length; i++) {
             System.out.print((8 - i) + " ");
             for (int j = 0; j < board[i].length; j++) {
                 printPiece(board[i][j]);
             }
             System.out.println();
         }
        System.out.print("  a b c d e f g h");
    }

    public static void printPiece(ChessPiece piece) {
        if (piece == null) {
            System.out.print("-");
        } else {
            System.out.print(piece);
        }
        System.out.print(" ");
    }
}
