package Exceptions;

public class OutsideOfGameboardException extends Exception {
    public OutsideOfGameboardException(String message) {
        super(message);
    }
}
