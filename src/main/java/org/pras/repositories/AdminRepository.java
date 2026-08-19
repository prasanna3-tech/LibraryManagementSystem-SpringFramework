package org.pras.repositories;

import org.pras.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository
        extends JpaRepository<Admin, Integer> {

    boolean existsByUsername(String username);
    Optional<Admin> findByUsernameAndPassword(
            String username,
            String password
    );
}