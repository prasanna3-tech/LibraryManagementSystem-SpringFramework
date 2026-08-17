package org.pras.exceptions;

public class StudentHasPendingFineException
        extends RuntimeException {

    public StudentHasPendingFineException(int studentId) {

        super("Cannot remove student " + studentId
                + " because they have a pending fine.");
    }
}