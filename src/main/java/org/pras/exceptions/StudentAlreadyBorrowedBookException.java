package org.pras.exceptions;

public class StudentAlreadyBorrowedBookException
        extends RuntimeException {

    public StudentAlreadyBorrowedBookException(
            int studentId,
            int bookId) {

        super(
                "Student " + studentId +
                        " has already borrowed book " + bookId
        );
    }
}