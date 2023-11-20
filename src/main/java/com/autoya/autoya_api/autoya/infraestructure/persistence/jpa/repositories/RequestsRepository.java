package com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Requests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JPA Repository interface for managing persistence operations related to requests.
 * Extends JpaRepository to inherit common CRUD operations.
 */
public interface RequestsRepository extends JpaRepository<Requests, Long> {
    List<Requests> findBytenant_Id(Long tenantId);
    List<Requests> findByVehicleId(String vehicleId);
}
