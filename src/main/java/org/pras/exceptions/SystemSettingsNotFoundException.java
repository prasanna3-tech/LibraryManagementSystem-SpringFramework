package org.pras.exceptions;

public class SystemSettingsNotFoundException
        extends RuntimeException {

    public SystemSettingsNotFoundException() {
        super("System settings not found.");
    }
}