package org.pras.exceptions;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(int studentId) {
        super("Student not found with id: " + studentId);
    }
}