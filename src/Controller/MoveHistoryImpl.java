package Controller;

import Model.Piece;
import Model.Square;
import Model.Model;
import Model.Gameboard;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Stack;

public class MoveHistoryImpl implements MoveHistory {

    Stack<Entry> moves;

    public MoveHistoryImpl(){
        moves = new Stack<>();
    }

    public void revertLastMove(Model model) {
        Entry lastMove = moves.pop();
        Piece piece = lastMove.piece;
        Gameboard board = model.getBoard();

        board.removePiece(piece.getSquare());
        piece.setSquare(lastMove.from);
        board.putPiece(piece);
        piece.decrementTimesMoved();
        if (lastMove.isCapturingMove()) {
            board.putPiece(lastMove.capturedPiece);
            model.getOpposingPieces(piece.getColor()).add(lastMove.capturedPiece);
        }
    }

    /**
     * Call this before editing model!!!
     * @param piece moving piece.
     * @param dest destination.
     * @param capturedPiece piece that is taken.
     */
    public void addMove(Piece piece, Square dest, Piece capturedPiece) {
        boolean hasMoved = piece.hasMoved();
        Square from = piece.getSquare();
        moves.add(new Entry(hasMoved, from, dest, piece, capturedPiece));
    }



    private class Entry {

        boolean isFirstMove;
        Square from, to;
        Piece piece, capturedPiece;

        public Entry(boolean isFirstMove, Square from, Square to, Piece piece, Piece capturedPiece) {
            this.isFirstMove = isFirstMove;
            this.from = from;
            this.to = to;
            this.piece = piece;
            this.capturedPiece = capturedPiece;
        }

        boolean isCapturingMove() {
            return capturedPiece != null;
        }
    }
}
