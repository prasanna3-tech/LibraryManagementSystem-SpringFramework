package org.pras.services;

import java.sql.*;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.pras.config.HibernateUtil;
import org.pras.exceptions.BookNotFoundException;
import org.pras.exceptions.StudentNotFoundException;
import org.pras.mappers.reportMappers.BorrowRecordReportMapper;
import org.pras.mappers.reportMappers.LibraryReportMapper;
import org.pras.models.BorrowRecord;
import org.pras.models.BorrowRecordReport;
import org.pras.models.LibraryReport;
import org.pras.models.Student;
import org.pras.repositories.BookRepository;
import org.pras.repositories.BorrowRecordRepository;
import org.pras.repositories.LibrarianRepository;
import org.pras.repositories.StudentRepository;
import org.pras.utils.DateUtilNew;
import org.springframework.stereotype.Service;


@Service
public class ReportService {


    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;
    private final LibrarianRepository librarianRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final LibraryReportMapper libraryReportMapper;

    private final BorrowRecordReportMapper borrowRecordReportMapper;


    public ReportService(
            BookRepository bookRepository,
            StudentRepository studentRepository,
            LibrarianRepository librarianRepository,
            BorrowRecordRepository borrowRecordRepository,
            LibraryReportMapper libraryReportMapper,
            BorrowRecordReportMapper borrowRecordReportMapper) {

        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
        this.librarianRepository = librarianRepository;
        this.borrowRecordRepository = borrowRecordRepository;
        this.libraryReportMapper = libraryReportMapper;
        this.borrowRecordReportMapper =
                borrowRecordReportMapper;
    }


    public LibraryReport getLibraryReport(Date todayDate) {

        long totalBooks =
                bookRepository.count();

        long totalStudents =
                studentRepository.count();

        long totalLibrarians =
                librarianRepository.count();

        long totalBorrowedBooks =
                borrowRecordRepository.countByReturnedFalse();

        List<Date> dueDates =
                borrowRecordRepository
                        .findDueDatesOfUnreturnedBooks();

        long totalOverdueBooks = 0;

        for (Date dueDate : dueDates) {

            if (DateUtilNew.getLateDays(
                    dueDate,
                    todayDate
            ) > 0) {


                totalOverdueBooks++;
            }
        }

        double totalFineCollected =
                borrowRecordRepository
                        .sumFineOfPaidRecords();

        return libraryReportMapper.toResult(
                totalBooks,
                totalStudents,
                totalLibrarians,
                totalBorrowedBooks,
                totalOverdueBooks,
                totalFineCollected
        );
    }


    public List<BorrowRecordReport> getAllBorrowRecords() {

        List<BorrowRecord> borrowRecords =
                borrowRecordRepository
                        .findAllBorrowRecordsForReport();

        return borrowRecords.stream()
                .map(borrowRecordReportMapper::toResult)
                .toList();
    }


    public List<BorrowRecordReport> getBorrowRecordsByStudentId(
            int studentId) {


        studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new StudentNotFoundException(studentId));

        List<BorrowRecord> borrowRecords =
                borrowRecordRepository
                        .findBorrowRecordsByStudentId(studentId);

        return borrowRecords.stream()
                .map(borrowRecordReportMapper::toResult)
                .toList();
    }

    public List<BorrowRecordReport> getBorrowRecordsByBookId(
            int bookId) {

        bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new BookNotFoundException(bookId));

        List<BorrowRecord> borrowRecords =
                borrowRecordRepository
                        .findBorrowRecordsByBookId(bookId);

        return borrowRecords.stream()
                .map(borrowRecordReportMapper::toResult)
                .toList();
    }

    public List<BorrowRecordReport> getBorrowRecordsByStatus(
            boolean returned) {

        List<BorrowRecord> borrowRecords =
                borrowRecordRepository
                        .findBorrowRecordsByStatus(returned);

        return borrowRecords.stream()
                .map(borrowRecordReportMapper::toResult)
                .toList();
    }
}