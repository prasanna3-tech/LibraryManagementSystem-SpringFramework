package org.pras.mappers;

import org.pras.dto.borrowDtos.BorrowHistoryResponseDto;
import org.pras.models.BorrowRecord;
import org.springframework.stereotype.Component;

@Component
public class BorrowHistoryResponseMapper {

    public BorrowHistoryResponseDto toResponseDto(
            BorrowRecord borrowRecord) {

        BorrowHistoryResponseDto dto =
                new BorrowHistoryResponseDto();

        dto.setTitle(
                borrowRecord.getBook().getTitle()
        );

        dto.setIssueDate(
                borrowRecord.getIssueDate()
        );

        dto.setDueDate(
                borrowRecord.getDueDate()
        );

        dto.setReturnDate(
                borrowRecord.getReturnDate()
        );

        dto.setReturned(
                borrowRecord.isReturned()
        );

        dto.setRenewCount(
                borrowRecord.getRenewCount()
        );

        dto.setFine(
                borrowRecord.getFine()
        );

        dto.setFinePaid(
                borrowRecord.isFinePaid()
        );

        return dto;
    }
}