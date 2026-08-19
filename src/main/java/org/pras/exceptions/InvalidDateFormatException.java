package org.pras.exceptions;

public class InvalidDateFormatException extends RuntimeException {

    public InvalidDateFormatException() {
        super("Invalid date format. Please use yyyy-MM-dd");
    }
}