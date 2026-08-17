package org.pras.exceptions;

public class InvalidStudentCredentialsException extends RuntimeException {

    public InvalidStudentCredentialsException() {
        super("Invalid student ID or password");
    }
}
