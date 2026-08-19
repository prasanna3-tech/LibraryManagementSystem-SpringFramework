package org.pras.exceptions;

public class InvalidLibrarianCredentialsException
        extends RuntimeException {

    public InvalidLibrarianCredentialsException() {
        super("Invalid username or password");
    }
}