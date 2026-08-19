package org.pras.mappers.adminMappers;

import org.pras.dto.adminDtos.AdminRegistrationRequestDto;
import org.pras.models.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminRequestMapper {

    public Admin toEntity(
            AdminRegistrationRequestDto request) {

        Admin admin = new Admin();

        admin.setUsername(request.getUsername());
        admin.setPassword(request.getPassword());

        return admin;
    }
}