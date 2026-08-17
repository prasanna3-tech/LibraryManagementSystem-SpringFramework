package org.pras.exceptions;

public class BookNotAvailableException extends RuntimeException {

    public BookNotAvailableException(int bookId) {
        super("Book is not available with id: " + bookId);
    }
}