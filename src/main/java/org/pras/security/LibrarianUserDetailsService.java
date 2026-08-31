package org.pras.security;

import org.pras.models.Librarian;
import org.pras.repositories.LibrarianRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LibrarianUserDetailsService implements UserDetailsService {

    private final LibrarianRepository librarianRepository;

    public LibrarianUserDetailsService(
            LibrarianRepository librarianRepository) {

        this.librarianRepository = librarianRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Librarian librarian =
                librarianRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "Librarian not found: " + username
                                )
                        );

        return new LmsUserDetails(
                librarian.getUsername(),
                librarian.getPassword(),
                librarian.getRole()
        );
    }
}
