package com.daydoodle.daydoodle.exceptions;

public class InvalidDateRangeException extends ApplicationException{
    public InvalidDateRangeException(String message) {
        super("INVALID_DATE_RANGE", message);
    }
}
