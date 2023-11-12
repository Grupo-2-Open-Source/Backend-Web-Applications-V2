package com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories;

import com.autoya.autoya_api.autoya.domain.model.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Tenant findTenantById(Long tenantId);
    Tenant findByEmail(String email);
}
