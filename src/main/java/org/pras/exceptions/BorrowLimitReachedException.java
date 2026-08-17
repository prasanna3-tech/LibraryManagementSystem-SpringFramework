package org.pras.exceptions;

public class BorrowLimitReachedException
        extends RuntimeException {

    public BorrowLimitReachedException(int studentId) {
        super(
                "Student has reached the maximum borrow limit: "
                        + studentId
        );
    }
}