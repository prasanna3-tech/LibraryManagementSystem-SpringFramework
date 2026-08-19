package org.pras.exceptions;

public class LibrarianNotFoundException extends RuntimeException {

    public LibrarianNotFoundException(int librarianId) {
        super("Librarian with ID " + librarianId + " not found");
    }
}