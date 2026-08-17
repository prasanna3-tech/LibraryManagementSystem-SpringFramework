package org.pras.exceptions;

public class StudentAlreadyReservedBookException
        extends RuntimeException {

    public StudentAlreadyReservedBookException(
            int studentId,
            int bookId) {

        super(
                "Student " + studentId
                        + " has already reserved book " + bookId
        );
    }
}