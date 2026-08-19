package org.pras.mappers.adminMappers;

import org.pras.dto.adminDtos.AdminResponseDto;
import org.pras.models.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminResponseMapper {

    public AdminResponseDto toResponseDto(Admin admin) {

        AdminResponseDto dto =
                new AdminResponseDto();

        dto.setAdminId(
                admin.getAdminId()
        );

        dto.setUsername(
                admin.getUsername()
        );

        return dto;
    }
}