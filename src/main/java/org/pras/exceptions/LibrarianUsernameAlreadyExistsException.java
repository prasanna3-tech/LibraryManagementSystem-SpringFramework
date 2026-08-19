package org.pras.exceptions;

public class LibrarianUsernameAlreadyExistsException
        extends RuntimeException {

    public LibrarianUsernameAlreadyExistsException(String username) {

        super("Username '" + username + "' already exists");
    }
}