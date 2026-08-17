package org.pras.exceptions;

public class BookAlreadyReservedException extends RuntimeException {

    public BookAlreadyReservedException(int bookId) {
        super("This book is already reserved: " + bookId);
    }
}