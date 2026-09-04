package org.pras.controllers;

import jakarta.validation.Valid;
import org.pras.dto.librarianDto.LibrarianLoginRequestDto;
import org.pras.dto.librarianDto.LibrarianRegistrationRequestDto;
import org.pras.dto.librarianDto.LibrarianResponseDto;
import org.pras.dto.librarianDto.UpdateLibrarianRequestDto;
import org.pras.mappers.librarianMappers.LibrarianRequestMapper;
import org.pras.mappers.librarianMappers.LibrarianResponseMapper;
import org.pras.models.Librarian;
import org.pras.services.LibrarianService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/librarians")
public class LibrarianController {

    private final LibrarianService librarianService;
    private final LibrarianResponseMapper librarianResponseMapper;
    private final LibrarianRequestMapper librarianRequestMapper;

    public LibrarianController(
            LibrarianService librarianService,
            LibrarianRequestMapper librarianRequestMapper,
            LibrarianResponseMapper librarianResponseMapper) {

        this.librarianService = librarianService;
        this.librarianRequestMapper = librarianRequestMapper;
        this.librarianResponseMapper = librarianResponseMapper;
    }

    @PostMapping
    public ResponseEntity<LibrarianResponseDto> addLibrarian(
            @Valid  @RequestBody LibrarianRegistrationRequestDto request) {

        Librarian librarian =
                librarianRequestMapper.toEntity(request);

        Librarian savedLibrarian =
                librarianService.addLibrarian(librarian);

        LibrarianResponseDto response =
                librarianResponseMapper.toResponseDto(
                        savedLibrarian
                );

        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<LibrarianResponseDto> loginLibrarian(
            @Valid @RequestBody LibrarianLoginRequestDto request) {

        Librarian librarian =
                librarianService.loginLibrarian(
                        request.getUsername(),
                        request.getPassword()
                );

        LibrarianResponseDto response =
                librarianResponseMapper.toResponseDto(librarian);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{librarianId}")
    public ResponseEntity<LibrarianResponseDto> removeLibrarian(
            @PathVariable("librarianId") int librarianId) {

        Librarian deletedLibrarian =
                librarianService.removeLibrarian(librarianId);

        LibrarianResponseDto response =
                librarianResponseMapper.toResponseDto(
                        deletedLibrarian
                );

        return ResponseEntity.ok(response);
    }

    @PreAuthorize(
            "hasRole('ADMIN') or @librarianSecurity.isOwner(#p0)"
    )
    @PutMapping("/{librarianId}")
    public ResponseEntity<LibrarianResponseDto> updateLibrarianDetails(
            @PathVariable("librarianId") int librarianId,
            @Valid @RequestBody UpdateLibrarianRequestDto request) {

        Librarian updatedLibrarian =
                librarianService.updateLibrarianDetails(
                        librarianId,
                        request.getName(),
                        request.getUsername(),
                        request.getPassword()
                );

        LibrarianResponseDto response =
                librarianResponseMapper.toResponseDto(
                        updatedLibrarian
                );

        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<LibrarianResponseDto>> getAllLibrarians() {

        List<Librarian> librarians =
                librarianService.getAllLibrarians();

        List<LibrarianResponseDto> response =
                librarians.stream()
                        .map(librarianResponseMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(response);
    }
}