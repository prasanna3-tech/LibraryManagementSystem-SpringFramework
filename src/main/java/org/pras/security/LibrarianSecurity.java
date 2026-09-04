package org.pras.security;

import org.pras.models.Librarian;
import org.pras.repositories.LibrarianRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("librarianSecurity")
public class LibrarianSecurity {

    private final LibrarianRepository librarianRepository;

    public LibrarianSecurity(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }

    public boolean isOwner(int librarianId) {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String username = authentication.getName();

        Librarian librarian =
                librarianRepository.findById(librarianId)
                        .orElse(null);

        if (librarian == null) {
            return false;
        }

        return librarian.getUsername().equals(username);
    }
}
