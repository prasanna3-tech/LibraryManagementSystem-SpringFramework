package org.pras.security;

import org.pras.models.Student;
import org.pras.repositories.StudentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("studentSecurity")
public class StudentSecurity {

    private final StudentRepository studentRepository;

    public StudentSecurity(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public boolean isOwner(int studentId) {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String username = authentication.getName();

        Student student =
                studentRepository.findById(studentId)
                        .orElse(null);

        System.out.println("Authenticated username: " + username);
        System.out.println("Requested student ID: " + studentId);

        if (student == null) {
            System.out.println("Student not found");
            return false;
        }

        System.out.println("Student username: " + student.getUsername());

        return student.getUsername().equals(username);
    }
}