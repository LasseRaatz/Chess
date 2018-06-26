package Controller;

import Model.Piece;
import Model.Square;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface MovementController {

    Set<Square> getLegalMoves(Piece piece);

    Set<Square> getLegalAttackingMoves(Collection<Piece> pieces);
}
