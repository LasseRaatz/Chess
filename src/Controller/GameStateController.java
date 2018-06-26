package Controller;

import Model.Piece;
import Model.Square;

import java.util.Set;

public interface GameStateController {

    void trimCheckedMoves(Piece piece, Set<Square> moves);
}
