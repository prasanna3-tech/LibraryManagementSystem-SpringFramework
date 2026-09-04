package org.pras.mappers.adminMappers;

import org.pras.dto.adminDtos.AdminRegistrationRequestDto;
import org.pras.models.Admin;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class AdminRequestMapper {

    private final PasswordEncoder passwordEncoder;
    public AdminRequestMapper(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }

    public Admin toEntity(
            AdminRegistrationRequestDto request) {

        Admin admin = new Admin();

        admin.setUsername(request.getUsername());
        admin.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        admin.setRole("ADMIN");

        return admin;
    }
}