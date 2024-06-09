package com.example.yoonnsshop.common.exception;

public class DatabaseDeletionException extends RuntimeException {
    public DatabaseDeletionException(String message) {
        super(message);
    }

    public DatabaseDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}