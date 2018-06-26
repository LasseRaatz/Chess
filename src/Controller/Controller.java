package Controller;

import Model.Model;
import Model.Piece;
import Model.Square;

import java.util.Collection;
import java.util.Set;

public class Controller {
    MovementController mc;
    GameStateController gsc;
    Model model;

    public Controller(MovementController mc, GameStateController gsc, Model model) {
        this.mc = mc;
        this.gsc = gsc;
        this.model = model;
    }

    public void trimCheckedMoves(Piece piece, Set<Square> moves) {
        gsc.trimCheckedMoves(piece, moves);
    }

    public Set<Square> getLegalMoves(Piece piece) {
        return mc.getLegalMoves(piece);
    }

    public Set<Square> getLegalAttackingMoves(Collection<Piece> pieces) {
        return mc.getLegalAttackingMoves(pieces);
    }

}
