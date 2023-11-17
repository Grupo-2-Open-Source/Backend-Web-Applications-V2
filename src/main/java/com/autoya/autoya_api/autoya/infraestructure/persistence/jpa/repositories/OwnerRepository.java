package com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories;

import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findOwnerById(Long ownerId);
    Owner findByEmail(String email);
    Owner findByImages_ImageUrl(String ImageUrl);
}
