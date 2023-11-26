package org.fpm.di;

public class ClassNotBoundException extends RuntimeException {
    public ClassNotBoundException(String message) {
        super(message);
    }
}
