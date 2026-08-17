package org.pras.exceptions;

public class NoPendingFineException extends RuntimeException {

    public NoPendingFineException(int studentId) {
        super("Student " + studentId + " has no pending fine.");
    }
}