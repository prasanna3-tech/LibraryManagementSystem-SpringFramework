package org.pras.controllers;

import org.pras.dto.reportDtos.BorrowRecordResponseDto;
import org.pras.dto.reportDtos.LibraryReportResponseDto;
import org.pras.mappers.reportMappers.*;
import org.pras.mappers.reportMappers.BorrowRecordResponseMapper;
import org.pras.mappers.reportMappers.LibraryReportResponseMapper;
import org.pras.models.BorrowRecordReport;
import org.pras.models.LibraryReport;
import org.pras.services.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;
    private final LibraryReportResponseMapper libraryReportResponseMapper;
  private final  BorrowRecordResponseMapper borrowRecordResponseMapper;

    public ReportController(
            ReportService reportService,
            LibraryReportResponseMapper libraryReportResponseMapper,
            BorrowRecordResponseMapper borrowRecordResponseMapper) {

        this.reportService = reportService;

        this.libraryReportResponseMapper =
                libraryReportResponseMapper;

        this.borrowRecordResponseMapper =
                borrowRecordResponseMapper;
    }

    @GetMapping("/library")
    public ResponseEntity<LibraryReportResponseDto> getLibraryReport(
            @RequestParam("todayDate") String todayDate) {

        System.out.println("Received date: " + todayDate);

        Date date = Date.valueOf(todayDate);

        LibraryReport report =
                reportService.getLibraryReport(date);

        LibraryReportResponseDto response =
                libraryReportResponseMapper.toResponseDto(report);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/borrow-records")
    public ResponseEntity<List<BorrowRecordResponseDto>> getAllBorrowRecords() {

        List<BorrowRecordReport> reports =
                reportService.getAllBorrowRecords();

        List<BorrowRecordResponseDto> response =
                reports.stream()
                        .map(borrowRecordResponseMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/borrow-records/student/{studentId}")
    public ResponseEntity<List<BorrowRecordResponseDto>>
    getBorrowRecordsByStudentId(
            @PathVariable("studentId") int studentId) {

        List<BorrowRecordReport> reports =
                reportService.getBorrowRecordsByStudentId(
                        studentId
                );

        List<BorrowRecordResponseDto> response =
                reports.stream()
                        .map(borrowRecordResponseMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/borrow-records/book/{bookId}")
    public ResponseEntity<List<BorrowRecordResponseDto>>
    getBorrowRecordsByBookId(
            @PathVariable("bookId") int bookId) {

        List<BorrowRecordReport> reports =
                reportService.getBorrowRecordsByBookId(bookId);

        List<BorrowRecordResponseDto> response =
                reports.stream()
                        .map(borrowRecordResponseMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/borrow-records/status")
    public ResponseEntity<List<BorrowRecordResponseDto>>
    getBorrowRecordsByStatus(
            @RequestParam("returned") boolean returned) {

        List<BorrowRecordReport> reports =
                reportService.getBorrowRecordsByStatus(returned);

        List<BorrowRecordResponseDto> response =
                reports.stream()
                        .map(borrowRecordResponseMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(response);
    }
}
