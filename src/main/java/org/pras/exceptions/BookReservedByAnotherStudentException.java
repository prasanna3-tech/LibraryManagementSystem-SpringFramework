package org.pras.exceptions;

public class BookReservedByAnotherStudentException
        extends RuntimeException {

    public BookReservedByAnotherStudentException(int bookId) {
        super(
                "This book is reserved by another student: " + bookId
        );
    }
}