package org.pras.exceptions;

public class AdminNotFoundException extends RuntimeException {

    public AdminNotFoundException(int adminId) {
        super("Admin with ID " + adminId + " not found");
    }
}