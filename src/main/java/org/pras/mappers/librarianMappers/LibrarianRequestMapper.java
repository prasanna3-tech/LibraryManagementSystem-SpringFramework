package org.pras.mappers.librarianMappers;

import org.pras.dto.librarianDto.LibrarianRegistrationRequestDto;
import org.pras.models.Librarian;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class LibrarianRequestMapper {

    private final PasswordEncoder passwordEncoder;

    public LibrarianRequestMapper(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }

    public Librarian toEntity(
            LibrarianRegistrationRequestDto request) {

        Librarian librarian = new Librarian();

        librarian.setName(request.getName());
        librarian.setUsername(request.getUsername());
        librarian.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        return librarian;
    }
}
