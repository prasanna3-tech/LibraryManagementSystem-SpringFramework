package org.pras.services;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.pras.config.HibernateUtil;
import org.pras.dto.borrowDtos.OverdueBookResponseDto;
import org.pras.exceptions.*;
import org.pras.models.*;
import org.pras.repositories.*;
import org.pras.utils.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class BorrowService {

    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final ReservationRepository reservationRepository;
    private final SystemSettingsRepository systemSettingsRepository;

    public BorrowService(
            BookRepository bookRepository,
            StudentRepository studentRepository,
            BorrowRecordRepository borrowRecordRepository,
            ReservationRepository reservationRepository,
            SystemSettingsRepository systemSettingsRepository) {

        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
        this.borrowRecordRepository = borrowRecordRepository;
        this.reservationRepository = reservationRepository;
        this.systemSettingsRepository = systemSettingsRepository;
    }

    @Transactional
    public BorrowRecord issueBook(
            int studentId,
            int bookId,
            Date issueDate,
            Date dueDate) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(studentId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new BookNotFoundException(bookId));

        if (book.getQuantity() <= 0) {
            throw new BookNotAvailableException(bookId);
        }

        Optional<BorrowRecord> existingBorrow =
                borrowRecordRepository
                        .findByStudentStudentIdAndBookBookIdAndReturnedFalse(
                                studentId,
                                bookId
                        );

        if (existingBorrow.isPresent()) {
            throw new StudentAlreadyBorrowedBookException(
                    studentId,
                    bookId
            );
        }

        Optional<Reservation> reservation =
                reservationRepository.findByBookBookId(bookId);

        if (reservation.isPresent()
                && reservation.get().getStudent().getStudentId() != studentId
                && book.getQuantity() == 1) {

            throw new BookReservedByAnotherStudentException(bookId);
        }

        long borrowedCount =
                borrowRecordRepository
                        .countByStudentStudentIdAndReturnedFalse(studentId);

        SystemSettings systemSettings =
                systemSettingsRepository.findById(1)
                        .orElseThrow(SystemSettingsNotFoundException::new);

        int maxBooksAllowed =
                systemSettings.getMaxBooksAllowed();

        if (borrowedCount >= maxBooksAllowed) {
            throw new BorrowLimitReachedException(studentId);
        }

        double totalFine =
                borrowRecordRepository.getTotalUnpaidFine(studentId);

        if (totalFine > 0) {
            throw new PendingFineException(studentId);
        }

        book.setQuantity(book.getQuantity() - 1);

        BorrowRecord borrowRecord = new BorrowRecord();

        borrowRecord.setStudent(student);
        borrowRecord.setBook(book);
        borrowRecord.setIssueDate(issueDate);
        borrowRecord.setDueDate(dueDate);

        BorrowRecord savedBorrowRecord =
                borrowRecordRepository.save(borrowRecord);

        Optional<Reservation> studentReservation =
                reservationRepository
                        .findByStudentStudentIdAndBookBookId(
                                studentId,
                                bookId
                        );

        if (studentReservation.isPresent()) {
            reservationRepository.delete(studentReservation.get());
        }

        return savedBorrowRecord;
    }

    public List<BorrowRecord> getBorrowedBooks(int studentId) {

        // Check student
        studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(studentId));

        return borrowRecordRepository
                .findByStudentStudentIdAndReturnedFalse(studentId);
    }

    public List<BorrowRecord> getDueDates(int studentId) {

        studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(studentId));

        return borrowRecordRepository
                .findByStudentStudentIdAndReturnedFalseOrderByDueDateAsc(
                        studentId
                );
    }

    public List<OverdueBookResponseDto> getOverdueBooks(
            int studentId,
            Date todayDate) {

        studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(studentId));

        SystemSettings settings =
                systemSettingsRepository.findById(1)
                        .orElseThrow(
                                SystemSettingsNotFoundException::new
                        );

        double finePerDay = settings.getFinePerDay();

        List<BorrowRecord> borrowRecords =
                borrowRecordRepository
                        .findByStudentStudentIdAndReturnedFalseOrderByDueDateAsc(
                                studentId
                        );

        List<OverdueBookResponseDto> overdueBooks =
                new ArrayList<>();

        for (BorrowRecord borrowRecord : borrowRecords) {

            Date dueDate = borrowRecord.getDueDate();

            int lateDays =
                    DateUtilNew.getLateDays(
                            dueDate,
                            todayDate
                    );

            if (lateDays > 0) {

                double fine = lateDays * finePerDay;

                overdueBooks.add(
                        new OverdueBookResponseDto(
                                borrowRecord.getBook().getTitle(),
                                dueDate,
                                lateDays,
                                fine
                        )
                );
            }
        }

        return overdueBooks;
    }

    public double getTotalFine(int studentId) {

        // Make sure the student exists
        studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(studentId));

        return borrowRecordRepository.getTotalUnpaidFine(studentId);
    }

    @Transactional
    public double payFine(int studentId, double amount) {

        // Calculate the actual fine from the database
        double totalFine =
                borrowRecordRepository.getTotalUnpaidFine(studentId);

        if (totalFine <= 0) {
            throw new NoPendingFineException(studentId);
        }

        // Verify the amount
        if (Double.compare(amount, totalFine) != 0) {
            throw new IncorrectFinePaymentException(
                    totalFine,
                    amount
            );
        }

        // Find all unpaid fines
        List<BorrowRecord> borrowRecords =
                borrowRecordRepository
                        .findByStudentStudentIdAndReturnedTrueAndFinePaidFalse(
                                studentId
                        );

        // Mark every unpaid fine as paid
        for (BorrowRecord borrowRecord : borrowRecords) {
            borrowRecord.setFinePaid(true);
        }

        return totalFine;
    }

    public List<BorrowRecord> getBorrowHistory(int studentId) {

        studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(studentId));

        return borrowRecordRepository
                .findByStudentStudentIdOrderByIssueDateDesc(
                        studentId
                );
    }

    @Transactional
    public BorrowRecord returnBook(
            int studentId,
            int bookId,
            Date returnDate) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(studentId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new BookNotFoundException(bookId));

        BorrowRecord borrowRecord =
                borrowRecordRepository
                        .findByStudentStudentIdAndBookBookIdAndReturnedFalse(
                                studentId,
                                bookId
                        )
                        .orElseThrow(() ->
                                new BorrowRecordNotFoundException(
                                        studentId,
                                        bookId
                                ));

        SystemSettings settings =
                systemSettingsRepository.findById(1)
                        .orElseThrow(
                                SystemSettingsNotFoundException::new
                        );

        double finePerDay =
                settings.getFinePerDay();

        int lateDays =
                DateUtilNew.getLateDays(
                        borrowRecord.getDueDate(),
                        returnDate
                );

        double fine =
                lateDays > 0
                        ? lateDays * finePerDay
                        : 0;

        borrowRecord.setReturned(true);
        borrowRecord.setReturnDate(returnDate);
        borrowRecord.setFine(fine);

        if (fine == 0) {
            borrowRecord.setFinePaid(true);
        } else {
            borrowRecord.setFinePaid(false);
        }

        Book book1 = borrowRecord.getBook();

        book1.setQuantity(
                book1.getQuantity() + 1
        );

        Optional<Reservation> reservation =
                reservationRepository.findByBookBookId(bookId);

        if (reservation.isPresent()) {
            reservation.get().setNotified(true);
        }

        return borrowRecord;
    }

    @Transactional
    public BorrowRecord renewBook(
            int studentId,
            int bookId,
            Date newDueDate) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(studentId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new BookNotFoundException(bookId));

        // 1. Find active borrow record
        BorrowRecord borrowRecord =
                borrowRecordRepository
                        .findByStudentStudentIdAndBookBookIdAndReturnedFalse(
                                studentId,
                                bookId
                        )
                        .orElseThrow(() ->
                                new BorrowRecordNotFoundException(
                                        studentId,
                                        bookId
                                ));

        // 2. Get system settings
        SystemSettings settings =
                systemSettingsRepository.findById(1)
                        .orElseThrow(
                                SystemSettingsNotFoundException::new
                        );

        // 3. Check renew limit
        if (borrowRecord.getRenewCount()
                >= settings.getMaxRenewCount()) {

            throw new RenewalLimitReachedException(
                    studentId,
                    bookId
            );
        }

        // 4. Check reservation
        Optional<Reservation> reservation =
                reservationRepository.findByBookBookId(bookId);

        if (reservation.isPresent() && book.getQuantity()==0) {

            throw new BookReservedByAnotherStudentException(
                    bookId
            );
        }

        // 5. Renew
        borrowRecord.setDueDate(newDueDate);

        borrowRecord.setRenewCount(
                borrowRecord.getRenewCount() + 1
        );

        return borrowRecord;
    }

    @Transactional
    public Reservation reserveBook(
            int studentId,
            int bookId,
            Date reservationDate) {

        // 1. Check Book

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new BookNotFoundException(bookId));

        // 2. Check Student

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new StudentNotFoundException(studentId));

        // 3. If quantity is exactly 1,
        // check whether the book is already reserved.

        if (book.getQuantity() == 1) {

            Optional<Reservation> existingReservation =
                    reservationRepository.findByBookBookId(bookId);

            if (existingReservation.isPresent()) {

                throw new BookAlreadyReservedException(bookId);
            }
        }

        // 4. If quantity is greater than 0,
        // the book is available, so no reservation is needed.

        if (book.getQuantity() > 0) {

            throw new BookAvailableException(bookId);
        }

        // 5. Check if this student already borrowed the book.

        Optional<BorrowRecord> borrowRecord =
                borrowRecordRepository
                        .findByStudentStudentIdAndBookBookIdAndReturnedFalse(
                                studentId,
                                bookId
                        );

        if (borrowRecord.isPresent()) {

            throw new StudentAlreadyBorrowedBookException(
                    studentId,
                    bookId
            );
        }

        // 6. Check existing reservation.

        Optional<Reservation> reservation =
                reservationRepository.findByBookBookId(bookId);

        if (reservation.isPresent()) {

            if (reservation.get()
                    .getStudent()
                    .getStudentId() == studentId) {

                throw new StudentAlreadyReservedBookException(
                        studentId,
                        bookId
                );
            }

            throw new BookAlreadyReservedException(bookId);
        }

        // 7. Create Reservation

        Reservation newReservation = new Reservation();

        newReservation.setStudent(student);
        newReservation.setBook(book);
        newReservation.setReservationDate(reservationDate);
        newReservation.setNotified(false);

        // 8. Save Reservation

        return reservationRepository.save(newReservation);
    }
}