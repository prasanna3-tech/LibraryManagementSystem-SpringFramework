package org.pras.services;



import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.pras.config.HibernateUtil;
import org.pras.exceptions.InvalidLibrarianCredentialsException;
import org.pras.exceptions.LibrarianNotFoundException;
import org.pras.exceptions.LibrarianUsernameAlreadyExistsException;
import org.pras.models.Librarian;
import org.pras.repositories.LibrarianRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class LibrarianService {

    private final LibrarianRepository librarianRepository;
    private final PasswordEncoder passwordEncoder;

    public LibrarianService(
            LibrarianRepository librarianRepository,
            PasswordEncoder passwordEncoder) {

        this.librarianRepository = librarianRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Transactional
    public Librarian addLibrarian(Librarian librarian) {

        if (librarianRepository.existsByUsername(
                librarian.getUsername())) {

            throw new LibrarianUsernameAlreadyExistsException(
                    librarian.getUsername()
            );
        }

        return librarianRepository.save(librarian);
    }

    public Librarian loginLibrarian(
            String username,
            String password) {

        return librarianRepository
                .findByUsernameAndPassword(
                        username,
                        password
                )
                .orElseThrow(
                        InvalidLibrarianCredentialsException::new
                );
    }

    @Transactional
    public Librarian removeLibrarian(int librarianId) {

        Librarian librarian =
                librarianRepository.findById(librarianId)
                        .orElseThrow(() ->
                                new LibrarianNotFoundException(librarianId));

        librarianRepository.delete(librarian);

        return librarian;
    }

    @Transactional
    public Librarian updateLibrarianDetails(
            int librarianId,
            String newName,
            String newUsername,
            String newPassword) {

        Librarian librarian =
                librarianRepository.findById(librarianId)
                        .orElseThrow(() ->
                                new LibrarianNotFoundException(librarianId));

        boolean usernameExists =
                librarianRepository
                        .existsByUsernameAndLibrarianIdNot(
                                newUsername,
                                librarianId
                        );

        if (usernameExists) {

            throw new LibrarianUsernameAlreadyExistsException(
                    newUsername
            );
        }

        librarian.setName(newName);
        librarian.setUsername(newUsername);
        String encodedPassword =
                passwordEncoder.encode(newPassword);
        librarian.setPassword(encodedPassword);

        return librarian;
    }
    public List<Librarian> getAllLibrarians() {

        return librarianRepository.findAll();

    }
}