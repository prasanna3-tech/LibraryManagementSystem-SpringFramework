package org.pras.mappers.adminMappers;

import org.pras.dto.adminDtos.SystemSettingsResponseDto;
import org.pras.models.SystemSettings;
import org.springframework.stereotype.Component;

@Component
public class SystemSettingsResponseMapper {

    public SystemSettingsResponseDto toResponseDto(
            SystemSettings settings) {

        SystemSettingsResponseDto dto =
                new SystemSettingsResponseDto();

        dto.setSettingsId(
                settings.getSettingsId()
        );

        dto.setFinePerDay(
                settings.getFinePerDay()
        );

        dto.setMaxBooksAllowed(
                settings.getMaxBooksAllowed()
        );

        dto.setBorrowDurationDays(
                settings.getBorrowDurationDays()
        );

        dto.setMaxRenewCount(
                settings.getMaxRenewCount()
        );

        return dto;
    }
}