package org.pras.exceptions;

public class InvalidAdminCredentialsException
        extends RuntimeException {

    public InvalidAdminCredentialsException() {
        super("Invalid username or password");
    }
}