package org.pras.mappers.reportMappers;

import org.pras.dto.reportDtos.LibraryReportResponseDto;
import org.pras.models.LibraryReport;
import org.springframework.stereotype.Component;

@Component
public class LibraryReportResponseMapper {

    public LibraryReportResponseDto toResponseDto(
            LibraryReport report) {

        LibraryReportResponseDto dto =
                new LibraryReportResponseDto();

        dto.setTotalBooks(
                report.getTotalBooks()
        );

        dto.setTotalStudents(
                report.getTotalStudents()
        );

        dto.setTotalLibrarians(
                report.getTotalLibrarians()
        );

        dto.setTotalBorrowedBooks(
                report.getTotalBorrowedBooks()
        );

        dto.setTotalOverdueBooks(
                report.getTotalOverdueBooks()
        );

        dto.setTotalFineCollected(
                report.getTotalFineCollected()
        );

        return dto;
    }
}