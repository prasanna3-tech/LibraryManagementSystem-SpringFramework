package org.pras.controllers;

import org.pras.dto.borrowDtos.*;
import org.pras.mappers.borrowRecordMappers.*;
import org.pras.mappers.reportMappers.*;
import org.pras.models.BorrowRecord;
import org.pras.models.Reservation;
import org.pras.services.BorrowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/borrow-records")
public class BorrowController {

    private final ReservationResponseMapper reservationResponseMapper;
    private final BorrowService borrowService;
    private final BorrowResponseMapper borrowResponseMapper;
    private final BorrowedBookResponseMapper borrowedBookResponseMapper;
    private final DueDateResponseMapper dueDateResponseMapper;
    private final BorrowHistoryResponseMapper borrowHistoryResponseMapper;

    public BorrowController(
            BorrowService borrowService,
            BorrowResponseMapper borrowResponseMapper,
            ReservationResponseMapper reservationResponseMapper,
            BorrowedBookResponseMapper borrowedBookResponseMapper,
            DueDateResponseMapper dueDateResponseMapper,
            BorrowHistoryResponseMapper borrowHistoryResponseMapper) {

        this.borrowService = borrowService;
        this.borrowResponseMapper = borrowResponseMapper;
        this.reservationResponseMapper = reservationResponseMapper;
        this.borrowedBookResponseMapper = borrowedBookResponseMapper;
        this.dueDateResponseMapper = dueDateResponseMapper;
        this.borrowHistoryResponseMapper = borrowHistoryResponseMapper;
    }

    @PostMapping("/issue")
    public ResponseEntity<BorrowResponseDto> issueBook(
            @RequestBody BorrowRequestDto request) {

        BorrowRecord borrowRecord =
                borrowService.issueBook(
                        request.getStudentId(),
                        request.getBookId(),
                        request.getIssueDate(),
                        request.getDueDate()
                );

        BorrowResponseDto response =
                borrowResponseMapper.toResponseDto(borrowRecord);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @PutMapping("/return")
    public ResponseEntity<BorrowResponseDto> returnBook(
            @RequestBody ReturnBookRequestDto request) {

        BorrowRecord borrowRecord =
                borrowService.returnBook(
                        request.getStudentId(),
                        request.getBookId(),
                        request.getReturnDate()
                );

        BorrowResponseDto response =
                borrowResponseMapper.toResponseDto(borrowRecord);

        return ResponseEntity.ok(response);
    }
    @PutMapping("/renew")
    public ResponseEntity<BorrowResponseDto> renewBook(
            @RequestBody RenewBookRequestDto request) {

        BorrowRecord borrowRecord =
                borrowService.renewBook(
                        request.getStudentId(),
                        request.getBookId(),
                        request.getNewDueDate()
                );

        BorrowResponseDto response =
                borrowResponseMapper.toResponseDto(borrowRecord);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/reserve")
    public ResponseEntity<ReservationResponseDto> reserveBook(
            @RequestBody ReserveBookRequestDto request) {

        Reservation reservation =
                borrowService.reserveBook(
                        request.getStudentId(),
                        request.getBookId(),
                        request.getReservationDate()
                );

        ReservationResponseDto response =
                reservationResponseMapper.toResponseDto(reservation);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping("/fine/{studentId}")
    public ResponseEntity<FineResponseDto> getTotalFine(
            @PathVariable("studentId") int studentId) {

        double totalFine =
                borrowService.getTotalFine(studentId);

        FineResponseDto response =
                new FineResponseDto(
                        studentId,
                        totalFine
                );

        return ResponseEntity.ok(response);
    }
    @PutMapping("/pay-fine")
    public ResponseEntity<FineResponseDto> payFine(
            @RequestBody PayFineRequestDto request) {

        double amountPaid =
                borrowService.payFine(
                        request.getStudentId(),
                        request.getAmount()
                );

        FineResponseDto response =
                new FineResponseDto(
                        request.getStudentId(),
                        amountPaid
                );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/student/{studentId}/borrowed-books")
    public ResponseEntity<List<BorrowedBookResponseDto>> getBorrowedBooks(
            @PathVariable("studentId") int studentId) {

        List<BorrowRecord> borrowRecords =
                borrowService.getBorrowedBooks(studentId);

        List<BorrowedBookResponseDto> response =
                borrowRecords.stream()
                        .map(borrowedBookResponseMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/student/{studentId}/due-dates")
    public ResponseEntity<List<DueDateResponseDto>> getDueDates(
            @PathVariable("studentId") int studentId) {

        List<BorrowRecord> borrowRecords =
                borrowService.getDueDates(studentId);

        List<DueDateResponseDto> response =
                borrowRecords.stream()
                        .map(dueDateResponseMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/student/{studentId}/overdue-books")
    public ResponseEntity<List<OverdueBookResponseDto>> getOverdueBooks(
            @PathVariable("studentId") int studentId,
            @RequestParam("todayDate") Date todayDate) {

        List<OverdueBookResponseDto> response =
                borrowService.getOverdueBooks(
                        studentId,
                        todayDate
                );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/student/{studentId}/history")
    public ResponseEntity<List<BorrowHistoryResponseDto>> getBorrowHistory(
            @PathVariable("studentId") int studentId) {

        List<BorrowRecord> borrowRecords =
                borrowService.getBorrowHistory(studentId);

        List<BorrowHistoryResponseDto> response =
                borrowRecords.stream()
                        .map(borrowHistoryResponseMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(response);
    }
}