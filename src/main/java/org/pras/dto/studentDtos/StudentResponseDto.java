package org.pras.dto.studentDtos;

public class StudentResponseDto {

    private int studentId;
    private String name;
    private String department;


    public StudentResponseDto() {
    }

    public StudentResponseDto(
            int studentId,
            String name,
            String department
            ) {

        this.studentId = studentId;
        this.name = name;
        this.department = department;

    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


}