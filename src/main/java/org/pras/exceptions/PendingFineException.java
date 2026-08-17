package org.pras.exceptions;

public class PendingFineException
        extends RuntimeException {

    public PendingFineException(int studentId) {
        super(
                "Student has a pending fine: " + studentId
        );
    }
}