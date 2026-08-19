package org.pras.repositories;

import org.pras.models.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface BorrowRecordRepository
        extends JpaRepository<BorrowRecord, Integer> {
    Optional<BorrowRecord> findByBookBookIdAndReturnedFalse(int bookId);

    Optional<BorrowRecord> findByStudentStudentIdAndBookBookIdAndReturnedFalse(
            int studentId,
            int bookId
    );

    @Query("""
    SELECT COALESCE(SUM(br.fine), 0)
    FROM BorrowRecord br
    WHERE br.student.studentId = :studentId
    AND br.returned = true
    AND br.finePaid = false
    """)
    double getTotalUnpaidFine( @Param("studentId") int studentId);

    @Query("""
    SELECT COALESCE(SUM(br.fine), 0)
    FROM BorrowRecord br
    WHERE br.student.studentId = :studentId
    AND br.returned = true
    AND br.finePaid = false
    """)
    double getTotalPendingFine(@Param("studentId") int studentId);

    List<BorrowRecord> findByStudentStudentIdAndReturnedFalse(
            int studentId
    );

    long countByStudentStudentIdAndReturnedFalse(int studentId);
    List<BorrowRecord>
    findByStudentStudentIdAndReturnedTrueAndFinePaidFalse(
            int studentId
    );
    List<BorrowRecord> findByStudentStudentIdAndReturnedFalseOrderByDueDateAsc(
            int studentId
    );
    List<BorrowRecord> findByStudentStudentIdOrderByIssueDateDesc(
            int studentId
    );
    boolean existsByStudentStudentIdAndReturnedFalse(
            int studentId
    );

    long countByReturnedFalse();

    @Query("""
        SELECT br.dueDate
        FROM BorrowRecord br
        WHERE br.returned = false
        """)
    List<Date> findDueDatesOfUnreturnedBooks();

    @Query("""
        SELECT COALESCE(SUM(br.fine), 0)
        FROM BorrowRecord br
        WHERE br.finePaid = true
        """)
    double sumFineOfPaidRecords();
    @Query("""
    SELECT br
    FROM BorrowRecord br
    ORDER BY br.issueDate DESC
    """)
    List<BorrowRecord> findAllBorrowRecordsForReport();
    @Query("""
    SELECT br
    FROM BorrowRecord br
    WHERE br.student.studentId = :studentId
    ORDER BY br.issueDate DESC
    """)
    List<BorrowRecord> findBorrowRecordsByStudentId(
            @Param("studentId")  int studentId
    );
    @Query("""
    SELECT br
    FROM BorrowRecord br
    WHERE br.book.bookId = :bookId
    ORDER BY br.issueDate DESC
    """)
    List<BorrowRecord> findBorrowRecordsByBookId(
            @Param("bookId") int bookId
    );
    @Query("""
    SELECT br
    FROM BorrowRecord br
    WHERE br.returned = :returned
    ORDER BY br.issueDate DESC
    """)
    List<BorrowRecord> findBorrowRecordsByStatus(
            @Param("returned") boolean returned
    );
}