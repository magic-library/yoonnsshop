package com.example.yoonnsshop.common.exception;

public class DatabaseInsertionException extends RuntimeException {
    public DatabaseInsertionException(String message) {
        super(message);
    }

    public DatabaseInsertionException(String message, Throwable cause) {
        super(message, cause);
    }
}