package org.pras.exceptions;

public class BookAvailableException extends RuntimeException {

    public BookAvailableException(int bookId) {
        super("Book is already available. No need to reserve: " + bookId);
    }
}