package org.pras.security;

import org.pras.models.Admin;
import org.pras.models.Librarian;
import org.pras.models.Student;
import org.pras.repositories.AdminRepository;
import org.pras.repositories.LibrarianRepository;
import org.pras.repositories.StudentRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LmsUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final LibrarianRepository librarianRepository;
    private final AdminRepository adminRepository;

    public LmsUserDetailsService(
            StudentRepository studentRepository,
            LibrarianRepository librarianRepository,
            AdminRepository adminRepository) {

        this.studentRepository = studentRepository;
        this.librarianRepository = librarianRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Student student = studentRepository.findByUsername(username)
                .orElse(null);

        if (student != null) {
            return new LmsUserDetails(
                    student.getUsername(),
                    student.getPassword(),
                    student.getRole()
            );
        }

        Librarian librarian = librarianRepository.findByUsername(username)
                .orElse(null);

        if (librarian != null) {
            return new LmsUserDetails(
                    librarian.getUsername(),
                    librarian.getPassword(),
                    librarian.getRole()
            );
        }

        Admin admin = adminRepository.findByUsername(username)
                .orElse(null);

        if (admin != null) {
            return new LmsUserDetails(
                    admin.getUsername(),
                    admin.getPassword(),
                    admin.getRole()
            );
        }

        throw new UsernameNotFoundException(
                "User not found: " + username
        );
    }
}