package Controller;

import Enums.Color;
import Model.Gameboard;
import Model.Model;
import Model.Piece;
import Model.Square;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class GameStateControllerImpl implements GameStateController {

    private Model model;
    private Controller controller;

    public GameStateControllerImpl(Model model) {
        this.model = model;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void trimCheckedMoves(Piece piece, Set<Square> moves) {
        Iterator<Square> iterator = moves.iterator();
        while(iterator.hasNext()) {
            model.movePiece(piece, iterator.next());
            if (isChecked(piece.getColor())) iterator.remove();
            model.revertLastMove();
        }
    }

    private boolean isChecked(Color color) {
        Square kingSquare = model.getKingSquare(color);
        List<Piece> opposingPieces = model.getOpposingPieces(color);
        return kingSquare.isIn(controller.getLegalAttackingMoves(opposingPieces));
    }
}
