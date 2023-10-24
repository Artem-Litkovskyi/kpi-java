package org.example;

public class UnacceptableArrayLengthException extends RuntimeException {
    public UnacceptableArrayLengthException() {}

    public UnacceptableArrayLengthException(String message) {
        super(message);
    }
}
