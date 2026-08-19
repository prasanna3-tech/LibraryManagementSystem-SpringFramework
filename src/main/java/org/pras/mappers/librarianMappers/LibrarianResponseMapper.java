package org.pras.mappers.librarianMappers;

import org.pras.dto.librarianDto.LibrarianResponseDto;
import org.pras.models.Librarian;
import org.springframework.stereotype.Component;

@Component
public class LibrarianResponseMapper {

    public LibrarianResponseDto toResponseDto(
            Librarian librarian) {

        LibrarianResponseDto dto =
                new LibrarianResponseDto();

        dto.setLibrarianId(
                librarian.getLibrarianId()
        );

        dto.setName(
                librarian.getName()
        );

        dto.setUsername(
                librarian.getUsername()
        );

        return dto;
    }
}
