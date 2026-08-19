package org.pras.dto.adminDtos;

public class AdminResponseDto {

    private int adminId;
    private String username;

    public AdminResponseDto() {
    }

    public AdminResponseDto(
            int adminId,
            String username) {

        this.adminId = adminId;
        this.username = username;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
