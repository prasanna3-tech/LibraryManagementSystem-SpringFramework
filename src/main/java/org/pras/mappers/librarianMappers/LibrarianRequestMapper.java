package org.pras.mappers.librarianMappers;

import org.pras.dto.librarianDto.LibrarianRegistrationRequestDto;
import org.pras.models.Librarian;
import org.springframework.stereotype.Component;

@Component
public class LibrarianRequestMapper {

    public Librarian toEntity(
            LibrarianRegistrationRequestDto request) {

        Librarian librarian = new Librarian();

        librarian.setName(request.getName());
        librarian.setUsername(request.getUsername());
        librarian.setPassword(request.getPassword());

        return librarian;
    }
}
