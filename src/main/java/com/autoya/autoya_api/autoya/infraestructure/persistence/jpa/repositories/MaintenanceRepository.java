package com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Images;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing persistence operations related to maintenance.
 * This interface extends JpaRepository to inherit common CRUD operations.
 */
@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
}
