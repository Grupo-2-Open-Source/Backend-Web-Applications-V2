package com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories;

import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository interface for managing persistence operations related to tenants.
 * Extends JpaRepository to inherit common CRUD operations.
 */
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Tenant findTenantById(Long tenantId);
    Tenant findByEmail(String email);
    Tenant findByImages_ImageUrl(String ImageUrl);
}
