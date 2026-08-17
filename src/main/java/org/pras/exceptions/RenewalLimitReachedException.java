package org.pras.exceptions;

public class RenewalLimitReachedException
        extends RuntimeException {

    public RenewalLimitReachedException(
            int studentId,
            int bookId) {

        super(
                "Renewal limit reached for student "
                        + studentId
                        + " and book "
                        + bookId
        );
    }
}