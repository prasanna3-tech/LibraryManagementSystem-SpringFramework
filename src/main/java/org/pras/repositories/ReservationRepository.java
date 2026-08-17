package org.pras.repositories;

import org.pras.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Optional<Reservation> findByBookBookId(int bookId);

    Optional<Reservation> findByStudentStudentIdAndBookBookId(
            int studentId,
            int bookId
    );
    List<Reservation> findByStudentStudentIdAndNotifiedTrue(
            int studentId
    );
}