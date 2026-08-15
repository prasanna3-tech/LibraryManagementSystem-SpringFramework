package org.pras.repositories;

import org.pras.models.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowRecordRepository
        extends JpaRepository<BorrowRecord, Integer> {
    Optional<BorrowRecord> findByBookBookIdAndReturnedFalse(int bookId);
}