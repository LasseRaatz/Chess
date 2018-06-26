package Model;

import Enums.Direction;
import Exceptions.OutsideOfGameboardException;

import java.util.Collection;

public class Square {

    private int letterSideCoord, numberSideCoord;

    public Square(char letter, int number) {
        letterSideCoord = calculateLetterSideCoord(letter);
        numberSideCoord = calculateNumberSideCoord(number);
    }

    public Square(int letterSideCoord, int numberSideCoord) throws OutsideOfGameboardException {
        this.letterSideCoord = letterSideCoord;
        this.numberSideCoord = numberSideCoord;
        validate();
    }

    public int getNumberSideCoord() {
        return numberSideCoord;
    }

    public int getLetterSideCoord() {
        return letterSideCoord;
    }

    public boolean equals(Square square) {
        return (this.numberSideCoord == square.numberSideCoord &&
                this.letterSideCoord == square.letterSideCoord);
    }

    public boolean isIn(Collection<Square> squares) {
        for (Square square : squares) {
            if (this.equals(square)) return true;
        }
        return false;
    }

    public int calculateLetterSideCoord(char letter) {
        letter = Character.toUpperCase(letter);
        switch (letter) {
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 6;
            case 'H':
                return 7;
            default:
                throw new IllegalArgumentException("Letter out of range");
        }
    }

    public int calculateNumberSideCoord(int number) {
        if (number > 8 || number < 0) throw new IllegalArgumentException("Number out of range");
        return number - 1;
    }

    public void validate() throws OutsideOfGameboardException {
        if (numberSideCoord < 0 || numberSideCoord > 7 || letterSideCoord < 0 || letterSideCoord > 7)
            throw new OutsideOfGameboardException("Square is outside gameboard");

    }

    public Square getNorthernSquare() throws OutsideOfGameboardException {
        return new Square(letterSideCoord, numberSideCoord + 1);
    }

    public Square getSouthernSquare() throws OutsideOfGameboardException {
        return new Square(letterSideCoord, numberSideCoord - 1);
    }

    public Square getEasternSquare() throws OutsideOfGameboardException {
        return new Square(letterSideCoord + 1, numberSideCoord);
    }

    public Square getWesternSquare() throws OutsideOfGameboardException {
        return new Square(letterSideCoord - 1, numberSideCoord);
    }

    public Square getNorthEasternSquare() throws OutsideOfGameboardException {
        return new Square(letterSideCoord + 1, numberSideCoord + 1);
    }

    public Square getNorthWesternSquare() throws OutsideOfGameboardException {
        return new Square(letterSideCoord - 1, numberSideCoord + 1);
    }

    public Square getSouthWesternSquare() throws OutsideOfGameboardException {
        return new Square(letterSideCoord - 1, numberSideCoord - 1);
    }

    public Square getSouthEasternSquare() throws OutsideOfGameboardException {
        return new Square(letterSideCoord + 1, numberSideCoord - 1);
    }

    public Square getNextSquare(Direction direction) throws OutsideOfGameboardException {
        switch (direction) {
            case North:
                return getNorthernSquare();
            case South:
                return getSouthernSquare();
            case East:
                return getEasternSquare();
            case West:
                return getWesternSquare();
            case NorthEast:
                return getNorthEasternSquare();
            case NorthWest:
                return getNorthWesternSquare();
            case SouthEast:
                return getSouthEasternSquare();
            case SouthWest:
                return getSouthWesternSquare();
            default:
                throw new IllegalArgumentException("Invalid direction");
        }
    }
}
