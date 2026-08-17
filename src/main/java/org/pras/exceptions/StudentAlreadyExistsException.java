package org.pras.exceptions;

public class StudentAlreadyExistsException extends RuntimeException {

    public StudentAlreadyExistsException(int studentId) {

        super("Student with ID " + studentId + " already exists");

    }
}