package org.pras.controllers;

import org.pras.dto.adminDtos.*;
import org.pras.mappers.adminMappers.AdminRequestMapper;
import org.pras.mappers.adminMappers.AdminResponseMapper;
import org.pras.mappers.adminMappers.SystemSettingsResponseMapper;
import org.pras.models.Admin;
import org.pras.models.SystemSettings;
import org.pras.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;
    private final AdminRequestMapper adminRequestMapper;
    private final AdminResponseMapper adminResponseMapper;
    private final SystemSettingsResponseMapper systemSettingsResponseMapper;

    public AdminController(
            AdminService adminService,
            AdminRequestMapper adminRequestMapper,
            AdminResponseMapper adminResponseMapper,
            SystemSettingsResponseMapper systemSettingsResponseMapper) {

        this.adminService = adminService;
        this.adminRequestMapper = adminRequestMapper;
        this.adminResponseMapper = adminResponseMapper;
        this.systemSettingsResponseMapper = systemSettingsResponseMapper;
    }

    @PostMapping
    public ResponseEntity<AdminResponseDto> addAdmin(
            @Valid @RequestBody AdminRegistrationRequestDto request) {

        Admin admin =
                adminRequestMapper.toEntity(request);

        Admin savedAdmin =
                adminService.addAdmin(admin);

        AdminResponseDto response =
                adminResponseMapper.toResponseDto(savedAdmin);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<AdminResponseDto> loginAdmin(
            @Valid @RequestBody AdminLoginRequestDto request) {

        Admin admin =
                adminService.loginAdmin(
                        request.getUsername(),
                        request.getPassword()
                );

        AdminResponseDto response =
                adminResponseMapper.toResponseDto(admin);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{adminId}")
    public ResponseEntity<AdminResponseDto> removeAdmin(
            @PathVariable("adminId") int adminId) {

        Admin deletedAdmin =
                adminService.removeAdmin(adminId);

        AdminResponseDto response =
                adminResponseMapper.toResponseDto(deletedAdmin);

        return ResponseEntity.ok(response);
    }
    @PreAuthorize(
            "hasRole('ADMIN')"
    )
    @PutMapping("/system-settings")
    public ResponseEntity<SystemSettingsResponseDto> updateSystemSettings(
            @Valid @RequestBody UpdateSystemSettingsRequestDto request) {

        SystemSettings updatedSettings =
                adminService.updateSystemSettings(
                        request.getFinePerDay(),
                        request.getMaxBooksAllowed(),
                        request.getBorrowDurationDays(),
                        request.getMaxRenewCount()
                );

        SystemSettingsResponseDto response =
                systemSettingsResponseMapper.toResponseDto(
                        updatedSettings
                );

        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<AdminResponseDto>> getAllAdmins() {

        List<Admin> admins =
                adminService.getAllAdmins();

        List<AdminResponseDto> response =
                admins.stream()
                        .map(adminResponseMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(response);
    }
}
