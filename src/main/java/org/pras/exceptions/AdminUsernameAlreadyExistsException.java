package org.pras.exceptions;

public class AdminUsernameAlreadyExistsException
        extends RuntimeException {

    public AdminUsernameAlreadyExistsException(String username) {

        super("Username '" + username + "' already exists");
    }
}