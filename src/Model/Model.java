package Model;

import Controller.MoveHistory;
import Controller.SetupController;
import Enums.Color;
import Enums.PieceType;
import Exceptions.OutsideOfGameboardException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Model {

    MoveHistory history;
    Gameboard board;
    List<Piece> blackPieces;
    List<Piece> whitePieces;

    public Model(SetupController setupController, MoveHistory history) {
        board = new Gameboard();
        this.history = history;
        blackPieces = new ArrayList<Piece>();
        whitePieces = new ArrayList<Piece>();
        setupController.setUp(this);
    }

    public Gameboard getBoard() {
        return board;
    }

    public List<Piece> getBlackPieces() {
        return blackPieces;
    }

    public List<Piece> getWhitePieces() {
        return whitePieces;
    }

    public Square getKingSquare(Color color) {
        Piece king;
        switch (color) {
            case White:
                king = getKing(whitePieces);
                break;
            case Black:
                king = getKing(blackPieces);
                break;
            default:
                throw new IllegalArgumentException("Color not found");
        }
        return king.getSquare();
    }

    public List<Piece> getOpposingPieces(Color color) {
        switch (color) {
            case Black:
                return whitePieces;
            case White:
                return blackPieces;
            default:
                throw new IllegalArgumentException("Color not found");
        }
    }

    private Piece getKing(Collection<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece.getType() == PieceType.King) return piece;
        }
        throw new IllegalStateException("No king found!!!");
    }

    public void revertLastMove() {
        history.revertLastMove(this);
    }

    public void movePiece(Piece piece, Square square) {
        Piece capturedPiece = board.getPiece(square);
        history.addMove(piece, square, capturedPiece);
        board.movePiece(piece, square);
    }
}
