package com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories;

import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing persistence operations related to owners.
 * This interface extends JpaRepository to inherit common CRUD operations.
 */
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findOwnerById(Long ownerId);
    Owner findByEmail(String email);
    Owner findByImages_ImageUrl(String ImageUrl);
}
