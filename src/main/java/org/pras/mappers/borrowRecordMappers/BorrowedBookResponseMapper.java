package org.pras.mappers.borrowRecordMappers;

import org.pras.dto.borrowDtos.BorrowedBookResponseDto;
import org.pras.models.BorrowRecord;
import org.springframework.stereotype.Component;

@Component
public class BorrowedBookResponseMapper {

    public BorrowedBookResponseDto toResponseDto(
            BorrowRecord borrowRecord) {

        BorrowedBookResponseDto dto =
                new BorrowedBookResponseDto();

        dto.setBookId(
                borrowRecord.getBook().getBookId()
        );

        dto.setTitle(
                borrowRecord.getBook().getTitle()
        );

        dto.setIssueDate(
                borrowRecord.getIssueDate()
        );

        dto.setDueDate(
                borrowRecord.getDueDate()
        );

        return dto;
    }
}