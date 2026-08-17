package org.pras.exceptions;

public class StudentHasBorrowedBooksException
        extends RuntimeException {

    public StudentHasBorrowedBooksException(int studentId) {

        super("Cannot remove student " + studentId
                + " because they have borrowed book(s).");
    }
}