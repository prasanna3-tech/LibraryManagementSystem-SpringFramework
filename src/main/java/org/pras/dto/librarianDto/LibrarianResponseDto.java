package org.pras.dto.librarianDto;

public class LibrarianResponseDto {

    private int librarianId;
    private String name;
    private String username;

    public LibrarianResponseDto() {
    }

    public LibrarianResponseDto(
            int librarianId,
            String name,
            String username) {

        this.librarianId = librarianId;
        this.name = name;
        this.username = username;
    }

    public int getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(int librarianId) {
        this.librarianId = librarianId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
