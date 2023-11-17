package com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories;

import com.autoya.autoya_api.autoya.domain.model.aggregate.CriminalRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriminalRequestsRepository extends JpaRepository<CriminalRecords, String> {
    CriminalRecords findByTenant_Id(Long tenantId);
}
