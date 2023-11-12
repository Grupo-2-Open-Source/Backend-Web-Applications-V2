package com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Images;
import com.autoya.autoya_api.autoya.domain.model.aggregate.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
}
