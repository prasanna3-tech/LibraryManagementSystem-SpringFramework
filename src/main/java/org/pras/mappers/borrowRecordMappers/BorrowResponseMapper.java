package org.pras.mappers.borrowRecordMappers;

import org.pras.dto.borrowDtos.BorrowResponseDto;
import org.pras.models.BorrowRecord;
import org.springframework.stereotype.Component;

@Component
public class BorrowResponseMapper {

    public BorrowResponseDto toResponseDto(BorrowRecord borrowRecord) {

        BorrowResponseDto dto = new BorrowResponseDto();

        dto.setBorrowRecordId(
                borrowRecord.getBorrowRecordId()
        );

        dto.setStudentId(
                borrowRecord.getStudent().getStudentId()
        );

        dto.setBookId(
                borrowRecord.getBook().getBookId()
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