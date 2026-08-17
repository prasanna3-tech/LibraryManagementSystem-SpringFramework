package org.pras.repositories;

import org.pras.models.SystemSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemSettingsRepository
        extends JpaRepository<SystemSettings, Integer> {
}
