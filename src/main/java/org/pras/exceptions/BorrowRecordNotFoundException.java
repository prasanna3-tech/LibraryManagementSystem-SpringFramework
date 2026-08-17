package org.pras.exceptions;

public class BorrowRecordNotFoundException
        extends RuntimeException {

    public BorrowRecordNotFoundException(
            int studentId,
            int bookId) {

        super(
                "No active borrow record found for student "
                        + studentId
                        + " and book "
                        + bookId
        );
    }
}