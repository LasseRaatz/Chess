package Model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Gameboard {

    private static final int LETTER_SIDE_DIMENSION = 8;
    private static final int NUMBER_SIDE_DIMENSION = 8;

    Piece[][] board; //[letterSide][numberSide]

    public Gameboard() {
        board = new Piece[LETTER_SIDE_DIMENSION][NUMBER_SIDE_DIMENSION];
    }

    public void movePiece(Piece piece, Square to) {
        Square from = piece.getSquare();
        piece.setSquare(to);
        removePiece(from);
        putPiece(piece);
    }

    public void putPiece(Piece piece) {
        Square square = piece.getSquare();
        if (!isVacant(square)) throw new IllegalArgumentException("Square taken");
        board[square.getLetterSideCoord()][square.getNumberSideCoord()] = piece;
    }

    public Piece getPiece(Square square) {
        return board[square.getLetterSideCoord()][square.getNumberSideCoord()];
    }

    public boolean isVacant(Square square) {
        return getPiece(square) == null;
    }

    public void removePiece(Square square) {
        board[square.getLetterSideCoord()][square.getNumberSideCoord()] = null;
    }
}
