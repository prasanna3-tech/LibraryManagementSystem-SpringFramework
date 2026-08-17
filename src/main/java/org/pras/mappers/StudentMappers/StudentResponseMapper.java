package org.pras.mappers.StudentMappers;

import org.pras.dto.studentDtos.StudentResponseDto;
import org.pras.models.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentResponseMapper {

    public StudentResponseDto toResponseDto(Student student) {

        StudentResponseDto dto =
                new StudentResponseDto();

        dto.setStudentId(
                student.getStudentId()
        );

        dto.setName(
                student.getName()
        );

        dto.setDepartment(
                student.getDepartment()
        );

        return dto;
    }
}