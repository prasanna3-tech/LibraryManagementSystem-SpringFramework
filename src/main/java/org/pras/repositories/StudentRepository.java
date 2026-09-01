package org.pras.repositories;

import org.pras.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByStudentIdAndPassword(
            int studentId,
            String password
    );
    Optional<Student> findByUsername(String username);
    boolean existsByUsername(String username);
}
