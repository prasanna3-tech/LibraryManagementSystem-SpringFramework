package org.pras.dto.studentDtos;

public class UpdateStudentRequestDto {

    private String name;
    private String department;

    public UpdateStudentRequestDto() {
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
