package org.example;

public class UnacceptableDimensionException extends RuntimeException {
    public UnacceptableDimensionException() {}

    public UnacceptableDimensionException(String message) {
        super(message);
    }
}
