package org.pras.exceptions;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(int bookId) {
        super("Book not found with id: " + bookId);
    }
}