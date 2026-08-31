package org.pras.security;

import org.pras.models.Student;
import org.pras.repositories.StudentRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StudentUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    public StudentUserDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        int studentId;

        try {
            studentId = Integer.parseInt(username);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException(
                    "Invalid student ID: " + username
            );
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Student not found: " + studentId
                        )
                );

        return new LmsUserDetails(
                String.valueOf(student.getStudentId()),
                student.getPassword(),
                student.getRole()
        );
    }
}