package org.pras.mappers;

import org.pras.dto.borrowDtos.DueDateResponseDto;
import org.pras.models.BorrowRecord;
import org.springframework.stereotype.Component;

@Component
public class DueDateResponseMapper {

    public DueDateResponseDto toResponseDto(
            BorrowRecord borrowRecord) {

        DueDateResponseDto dto =
                new DueDateResponseDto();

        dto.setTitle(
                borrowRecord.getBook().getTitle()
        );

        dto.setDueDate(
                borrowRecord.getDueDate()
        );

        return dto;
    }
}