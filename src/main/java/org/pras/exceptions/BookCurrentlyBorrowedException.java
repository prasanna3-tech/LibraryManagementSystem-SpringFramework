package org.pras.exceptions;

public class BookCurrentlyBorrowedException extends RuntimeException {

    public BookCurrentlyBorrowedException(int bookId) {
        super("Book cannot be removed because it is currently borrowed: " + bookId);
    }
}