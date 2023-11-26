package org.fpm.di;

public class NoValidConstructorException extends RuntimeException {
    public NoValidConstructorException(String message) {
        super(message);
    }
}
