package org.pras.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFound(
            BookNotFoundException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
    @ExceptionHandler(BookCurrentlyBorrowedException.class)
    public ResponseEntity<String> handleBookCurrentlyBorrowed(
            BookCurrentlyBorrowedException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFound(
            StudentNotFoundException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<String> handleBookNotAvailable(
            BookNotAvailableException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(StudentAlreadyBorrowedBookException.class)
    public ResponseEntity<String> handleAlreadyBorrowed(
            StudentAlreadyBorrowedBookException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(BookReservedByAnotherStudentException.class)
    public ResponseEntity<String> handleBookReserved(
            BookReservedByAnotherStudentException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(SystemSettingsNotFoundException.class)
    public ResponseEntity<String> handleSystemSettingsNotFound(
            SystemSettingsNotFoundException e) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
    @ExceptionHandler(BorrowLimitReachedException.class)
    public ResponseEntity<String> handleBorrowLimitReached(
            BorrowLimitReachedException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(PendingFineException.class)
    public ResponseEntity<String> handlePendingFine(
            PendingFineException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(BorrowRecordNotFoundException.class)
    public ResponseEntity<String> handleBorrowRecordNotFound(
            BorrowRecordNotFoundException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
    @ExceptionHandler(RenewalLimitReachedException.class)
    public ResponseEntity<String> handleRenewalLimitReached(
            RenewalLimitReachedException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(BookAvailableException.class)
    public ResponseEntity<String> handleBookAvailable(
            BookAvailableException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(BookAlreadyReservedException.class)
    public ResponseEntity<String> handleBookAlreadyReserved(
            BookAlreadyReservedException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(StudentAlreadyReservedBookException.class)
    public ResponseEntity<String> handleStudentAlreadyReserved(
            StudentAlreadyReservedBookException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(NoPendingFineException.class)
    public ResponseEntity<String> handleNoPendingFine(
            NoPendingFineException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(IncorrectFinePaymentException.class)
    public ResponseEntity<String> handleIncorrectFinePayment(
            IncorrectFinePaymentException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<String> handleStudentAlreadyExists(
            StudentAlreadyExistsException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(InvalidStudentCredentialsException.class)
    public ResponseEntity<String> handleInvalidStudentCredentials(
            InvalidStudentCredentialsException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }
    @ExceptionHandler(StudentHasBorrowedBooksException.class)
    public ResponseEntity<String> handleStudentHasBorrowedBooks(
            StudentHasBorrowedBooksException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(StudentHasPendingFineException.class)
    public ResponseEntity<String> handleStudentHasPendingFine(
            StudentHasPendingFineException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(LibrarianUsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleLibrarianUsernameAlreadyExists(
            LibrarianUsernameAlreadyExistsException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(InvalidLibrarianCredentialsException.class)
    public ResponseEntity<String> handleInvalidLibrarianCredentials(
            InvalidLibrarianCredentialsException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(
            MethodArgumentNotValidException e) {

        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(message);
    }
    @ExceptionHandler(LibrarianNotFoundException.class)
    public ResponseEntity<String> handleLibrarianNotFound(
            LibrarianNotFoundException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
    @ExceptionHandler(AdminUsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleAdminUsernameAlreadyExists(
            AdminUsernameAlreadyExistsException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(InvalidAdminCredentialsException.class)
    public ResponseEntity<String> handleInvalidAdminCredentials(
            InvalidAdminCredentialsException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }
    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<String> handleAdminNotFound(
            AdminNotFoundException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}