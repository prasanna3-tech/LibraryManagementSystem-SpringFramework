package org.pras.repositories;

import org.pras.models.Admin;
import org.pras.models.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibrarianRepository
        extends JpaRepository<Librarian, Integer> {

    boolean existsByUsername(String username);
    Optional<Librarian> findByUsernameAndPassword(
            String username,
            String password
    );
    boolean existsByUsernameAndLibrarianIdNot(
            String username,
            int librarianId
    );

    Optional<Librarian> findByUsername(String username);
}