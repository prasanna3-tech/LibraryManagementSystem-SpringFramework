package org.pras.mappers.reportMappers;

import org.pras.dto.reportDtos.BorrowRecordResponseDto;
import org.pras.models.BorrowRecordReport;
import org.springframework.stereotype.Component;

@Component
public class BorrowRecordResponseMapper {

    public BorrowRecordResponseDto toResponseDto(
            BorrowRecordReport report) {

        BorrowRecordResponseDto dto =
                new BorrowRecordResponseDto();

        dto.setBorrowId(
                report.getBorrowId()
        );

        dto.setStudentId(
                report.getStudentId()
        );

        dto.setStudentName(
                report.getStudentName()
        );

        dto.setBookId(
                report.getBookId()
        );

        dto.setBookTitle(
                report.getBookTitle()
        );

        dto.setIssueDate(
                report.getIssueDate()
        );

        dto.setDueDate(
                report.getDueDate()
        );

        dto.setReturnDate(
                report.getReturnDate()
        );

        dto.setReturned(
                report.isReturned()
        );

        dto.setRenewCount(
                report.getRenewCount()
        );

        dto.setFine(
                report.getFine()
        );

        dto.setFinePaid(
                report.isFinePaid()
        );

        return dto;
    }
}