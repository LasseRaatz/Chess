package Controller;

import Enums.Color;
import Enums.PieceType;
import Exceptions.OutsideOfGameboardException;
import Model.Piece;
import Model.Square;
import Model.Model;

public class StandardSetup implements SetupController {

    Model model;

    public void setUp(Model model) {
        this.model = model;
        setUpKings();
        setUpQueens();
        setUpBishops();
        setUpKnights();
        setUpRooks();
        setUpPawns();
    }

    private void setUpKings() {
        Piece blackKing = new Piece(PieceType.King, Color.Black, new Square('E', 8));
        addPieceToBoardAndList(blackKing);
        Piece whiteKing = new Piece(PieceType.King, Color.White, new Square('E', 1));
        addPieceToBoardAndList(whiteKing);
    }

    private void setUpQueens() {
        Piece blackQueen = new Piece(PieceType.Queen, Color.Black, new Square('D', 8));
        addPieceToBoardAndList(blackQueen);
        Piece whiteQueen = new Piece(PieceType.Queen, Color.White, new Square('D', 1));
        addPieceToBoardAndList(whiteQueen);
    }

    private void setUpBishops() {
        Piece blackBishop1 = new Piece(PieceType.Bishop, Color.Black, new Square('C',8));
        Piece blackBishop2 = new Piece(PieceType.Bishop, Color.Black, new Square('F',8));
        addPieceToBoardAndList(blackBishop1);
        addPieceToBoardAndList(blackBishop2);
        Piece whiteBishop1 = new Piece(PieceType.Bishop, Color.White, new Square('C',1));
        Piece whiteBishop2 = new Piece(PieceType.Bishop, Color.White, new Square('F',1));
        addPieceToBoardAndList(whiteBishop1);
        addPieceToBoardAndList(whiteBishop2);
    }

    private void setUpKnights() {
        Piece blackKnight1 = new Piece(PieceType.Knight, Color.Black, new Square('B',8));
        Piece blackKnight2 = new Piece(PieceType.Knight, Color.Black, new Square('G',8));
        addPieceToBoardAndList(blackKnight1);
        addPieceToBoardAndList(blackKnight2);
        Piece whiteKnight1 = new Piece(PieceType.Knight, Color.White, new Square('B',1));
        Piece whiteKnight2 = new Piece(PieceType.Knight, Color.White, new Square('G',1));
        addPieceToBoardAndList(whiteKnight1);
        addPieceToBoardAndList(whiteKnight2);
    }

    private void setUpRooks() {
        Piece blackRook1 = new Piece(PieceType.Rook, Color.Black, new Square('A',8));
        Piece blackRook2 = new Piece(PieceType.Rook, Color.Black, new Square('H',8));
        addPieceToBoardAndList(blackRook1);
        addPieceToBoardAndList(blackRook2);
        Piece whiteRook1 = new Piece(PieceType.Rook, Color.White, new Square('A',1));
        Piece whiteRook2 = new Piece(PieceType.Rook, Color.White, new Square('H',1));
        addPieceToBoardAndList(whiteRook1);
        addPieceToBoardAndList(whiteRook2);
    }

    private void setUpPawns() {
        try {
            setUpPawnRow(Color.Black, 7);
            setUpPawnRow(Color.White, 2);
        } catch (OutsideOfGameboardException e) {
            throw new IllegalStateException("Pawns could not be created");
        }
    }

    private void setUpPawnRow(Color color, int rowNumber) throws OutsideOfGameboardException {
        int numberSideCoord = rowNumber - 1;
        for (int i = 0; i < 8; ++i) {
            Piece pawn = new Piece(PieceType.Pawn, color, new Square(i, numberSideCoord));
        }
    }

    private void addPieceToBoardAndList(Piece piece) {
        addPieceToList(piece);
        model.getBoard().putPiece(piece);
    }

    private void addPieceToList(Piece piece) {
        switch (piece.getColor()) {
            case Black:
                model.getBlackPieces().add(piece);
                break;
            case White:
                model.getWhitePieces().add(piece);
                break;
            default:
                throw new IllegalArgumentException("Color not found");
        }
    }
}
