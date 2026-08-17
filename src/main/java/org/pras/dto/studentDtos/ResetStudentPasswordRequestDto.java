package org.pras.dto.studentDtos;

public class ResetStudentPasswordRequestDto {

    private String newPassword;

    public ResetStudentPasswordRequestDto() {
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}