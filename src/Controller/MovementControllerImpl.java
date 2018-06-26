package Controller;

import Enums.Color;
import Enums.Direction;
import Exceptions.OutsideOfGameboardException;
import Model.Gameboard;
import Model.Piece;
import Model.Square;

import java.util.*;

public class MovementControllerImpl implements MovementController {
    private Gameboard board;
    private Controller controller;

    public MovementControllerImpl(Gameboard board) {
        this.board = board;
        this.controller = controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public Set<Square> getLegalAttackingMoves(Collection<Piece> pieces) {
        HashSet<Square> legalMoves = new HashSet<>();
        for (Piece piece : pieces) {
            legalMoves.addAll(generateLegalMoveSet(piece, true));
        }
        return legalMoves;
    }

    @Override
    public Set<Square> getLegalMoves(Piece piece) {
        Set<Square> moves = generateLegalMoveSet(piece, false);
        controller.trimCheckedMoves(piece, moves);
        return moves;
    }

    private HashSet<Square> generateLegalMoveSet(Piece piece, boolean addAttackingMovesOnly) {
        HashSet<Square> legalMoves = new HashSet<>();
        switch (piece.getType()) {
            case King:
                generateKingMoves(piece, legalMoves);
                break;
            case Queen:
                generateQueenMoves(piece, legalMoves);
                break;
            case Rook:
                generateRookMoves(piece, legalMoves);
                break;
            case Knight:
                generateKnightMoves(piece, legalMoves);
                break;
            case Bishop:
                generateBishopMoves(piece, legalMoves);
                break;
            case Pawn:
                generatePawnMoves(piece, legalMoves, addAttackingMovesOnly);
                break;
            default:
                throw new IllegalArgumentException("Invalid PieceType");
        }
        return legalMoves;
    }

    private void generateKingMoves(Piece piece, Set<Square> legalMoves) {
        for (Direction direction : Direction.values()) {
            generateStepMoves(direction, piece, legalMoves);
        }
    }

    private void generateQueenMoves(Piece piece, Set<Square> legalMoves) {
        addDiagonalMoves(legalMoves, piece);
        addStraightMoves(legalMoves, piece);
    }

    private void generateRookMoves(Piece piece, Set<Square> legalMoves) {
        addStraightMoves(legalMoves, piece);
    }

    private void generateKnightMoves(Piece piece, Set<Square> legalMoves) {
        generateKnightJumps(piece, legalMoves);
    }

    private void generateBishopMoves(Piece piece, Set<Square> legalMoves) {
        addDiagonalMoves(legalMoves, piece);
    }

    private void generatePawnMoves(Piece piece, Set<Square> legalMoves, boolean addAttackingMovesOnly) {
        if(!addAttackingMovesOnly) generatePawnForwardMovement(piece, legalMoves);
        generatePawnAttackMoves(piece, legalMoves);
    }

    private void generatePawnForwardMovement(Piece piece, Set<Square> legalMoves) {
        if (isVacant(forwardSquare(piece))) {
            Square square = forwardSquare(piece);
            legalMoves.add(square);
            if (!piece.hasMoved()) addMoveIfVacant(forwardSquare(piece, square), legalMoves);
        }
    }

    private void generatePawnAttackMoves(Piece piece, Set<Square> legalMoves) {
        Square forwardLeftDiagonal = forwardLeftDiagonalSquare(piece);
        addMoveIfOccupiedByOpponent(forwardLeftDiagonal, piece, legalMoves);
        Square forwardRightDiagonal = forwardRightDiagonalSquare(piece);
        addMoveIfOccupiedByOpponent(forwardRightDiagonal, piece, legalMoves);
    }

    private void addMoveIfVacant(Square square, Set<Square> legalMoves) {
        if (isVacant(square)) legalMoves.add(square);
    }

    private void addMoveIfOccupiedByOpponent(Square square, Piece piece, Set<Square> legalMoves) {
        if (isOccupiedByOpponent(square, piece)) legalMoves.add(square);
    }

    private void generateKnightJumps(Piece piece, Set<Square> legalMoves) {
        generateKnightJump(piece, legalMoves, Direction.NorthEast, Direction.North, Direction.East);
        generateKnightJump(piece, legalMoves, Direction.NorthWest, Direction.North, Direction.West);
        generateKnightJump(piece, legalMoves, Direction.SouthEast, Direction.South, Direction.East);
        generateKnightJump(piece, legalMoves, Direction.SouthWest, Direction.South, Direction.West);
    }

    private void generateKnightJump(Piece piece, Set<Square> legalMoves, Direction diagonal, Direction adj1, Direction adj2) {
        try {
            Square diagonalSquare = piece.getSquare().getNextSquare(diagonal);
            generateKnightJump(piece, diagonalSquare, legalMoves, adj1, adj2);
        } catch (OutsideOfGameboardException e) {}
    }

    private void generateKnightJump(Piece piece, Square diagonalSquare, Set<Square> legalMoves, Direction adj1, Direction adj2) {
        generateKnightJump(piece, diagonalSquare, legalMoves, adj1);
        generateKnightJump(piece, diagonalSquare, legalMoves, adj2);
    }

    private void generateKnightJump(Piece piece, Square diagonalSquare, Set<Square> legalMoves, Direction adj) {
        try {
            Square square = diagonalSquare.getNextSquare(adj);
            if (isVacantOrOccupiedByOpponent(square, piece)) legalMoves.add(square);
        } catch (OutsideOfGameboardException e) { }
    }

    private void addDiagonalMoves(Set<Square> legalMoves, Piece piece) {
        generateDirectionalMoves(Direction.NorthWest, piece, legalMoves);
        generateDirectionalMoves(Direction.NorthEast, piece, legalMoves);
        generateDirectionalMoves(Direction.SouthWest, piece, legalMoves);
        generateDirectionalMoves(Direction.SouthEast, piece, legalMoves);
    }

    private void addStraightMoves(Set<Square> legalMoves, Piece piece) {
        generateDirectionalMoves(Direction.North, piece, legalMoves);
        generateDirectionalMoves(Direction.South, piece, legalMoves);
        generateDirectionalMoves(Direction.East, piece, legalMoves);
        generateDirectionalMoves(Direction.West, piece, legalMoves);
    }

    private void generateDirectionalMoves(Direction direction, Piece piece, Set<Square> legalMoves) {
        try {
            moveGeneration(direction, piece, legalMoves);
        } catch (OutsideOfGameboardException e) {}
    }

    private void moveGeneration(Direction direction, Piece piece, Set<Square> legalMoves) throws OutsideOfGameboardException {
        Square square = piece.getSquare().getNextSquare(direction);
        while(isVacant(square)) {
            legalMoves.add(square);
            square = square.getNextSquare(direction);
        }
        if (isOccupiedByOpponent(square, piece)) legalMoves.add(square);
    }

    private boolean isVacantOrOccupiedByOpponent(Square square, Piece piece) {
        return (isVacant(square) || isOccupiedByOpponent(square, piece));
    }

    private boolean isVacant(Square square) {
        return board.isVacant(square);
    }

    private boolean isOccupiedByOpponent(Square square, Piece piece) {
        if (isVacant(square)) return false;
        return (board.getPiece(square).getColor() != piece.getColor());
    }

    private void generateStepMoves(Direction direction, Piece piece, Set<Square> legalMoves) {
        try {
            stepGeneration(direction, piece, legalMoves);
        } catch (OutsideOfGameboardException e) {}
    }

    private void stepGeneration(Direction direction, Piece piece, Set<Square> legalMoves) throws OutsideOfGameboardException {
        Square square = piece.getSquare().getNextSquare(direction);
        if (isVacantOrOccupiedByOpponent(square, piece)) legalMoves.add(square);
    }

    private Square forwardSquare(Piece piece) {
        return getOrientationDependantNextSquare(piece, piece.getSquare(), Direction.South, Direction.North);
    }

    private Square forwardSquare(Piece piece, Square square) {
        return getOrientationDependantNextSquare(piece, square, Direction.South, Direction.North);
    }

    /**
     * Returns the next square in the appropriate direction to the input square.
     * If the piece is black the direction will be blackDir, if white whiteDir.
     * @param piece the given piece.
     * @param square the given square.
     * @param blackDir the direction in which a black piece would be moving.
     * @param whiteDir the direction in which a white piece would be moving.
     * @return the next square in the given direction.
     */
    private Square getOrientationDependantNextSquare(Piece piece, Square square, Direction blackDir, Direction whiteDir) {
        try {
            Color color = piece.getColor();
            if (color == Color.Black) return square.getNextSquare(blackDir);
            if (color == Color.White) return square.getNextSquare(whiteDir);
            throw new IllegalArgumentException("Unknown color");
        } catch (OutsideOfGameboardException e) { }
        return null;
    }

    private Square forwardLeftDiagonalSquare(Piece piece) {
        return getOrientationDependantNextSquare(piece, piece.getSquare(), Direction.SouthEast, Direction.NorthWest);
    }

    private Square forwardRightDiagonalSquare(Piece piece) {
        return getOrientationDependantNextSquare(piece, piece.getSquare(), Direction.SouthWest, Direction.NorthEast);
    }


}
