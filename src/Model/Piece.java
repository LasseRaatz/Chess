package Model;

import Enums.Color;
import Enums.PieceType;

public class Piece {
    private Square square;
    private PieceType type;
    private Color color;
    private int timesMoved;

    public Piece(PieceType type, Color color, Square square) {
        this.type = type;
        this.color = color;
        this.square = square;
        timesMoved = 0;
    }

    public void promote(PieceType type) {
        this.type = type;
    }

    public PieceType getType() {
        return type;
    }

    public boolean hasMoved() {
        return timesMoved == 0;
    }

    public void setTimesMoved(int timesMoved) {
        this.timesMoved = timesMoved;
    }

    public int getTimesMoved() { return timesMoved; }

    public void decrementTimesMoved() {
        timesMoved--;
    }

    public Color getColor() {
        return color;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }
}
