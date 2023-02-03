package com.javarush.parfenov.exception;

public class QException extends RuntimeException {
    public QException() {
        super();
    }

    public QException(String message) {
        super(message);
    }

    public QException(String message, Throwable cause) {
        super(message, cause);
    }

    public QException(Throwable cause) {
        super(cause);
    }

    protected QException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
