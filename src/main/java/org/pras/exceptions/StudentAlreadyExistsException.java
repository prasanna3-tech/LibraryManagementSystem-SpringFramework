package org.pras.exceptions;

public class StudentAlreadyExistsException extends RuntimeException {

    public StudentAlreadyExistsException(String userName) {

        super("Student with ID " + userName + " already exists");

    }
}