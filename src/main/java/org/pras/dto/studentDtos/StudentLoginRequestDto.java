package org.pras.dto.studentDtos;

public class StudentLoginRequestDto {

    private int studentId;
    private String password;

    public StudentLoginRequestDto() {
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}