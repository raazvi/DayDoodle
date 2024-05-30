package com.daydoodle.daydoodle.exceptions;

public abstract class ApplicationException extends RuntimeException {
    private final String errorCode;

    public ApplicationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
