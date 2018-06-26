package Controller;

import Model.Piece;
import Model.Square;
import Model.Model;

public interface MoveHistory {

    void revertLastMove(Model model);

    void addMove(Piece piece, Square dest, Piece capturedPiece);
}
