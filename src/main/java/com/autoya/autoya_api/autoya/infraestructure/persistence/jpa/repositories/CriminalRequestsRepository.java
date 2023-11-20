package com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories;

import com.autoya.autoya_api.autoya.domain.model.aggregate.CriminalRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing persistence operations related to criminal records.
 * This interface extends JpaRepository to inherit common CRUD operations.
 */
@Repository
public interface CriminalRequestsRepository extends JpaRepository<CriminalRecords, String> {
    CriminalRecords findByTenant_Id(Long tenantId);
}
