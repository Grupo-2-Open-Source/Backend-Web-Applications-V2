package com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing persistence operations related to images.
 * This interface extends JpaRepository to inherit common CRUD operations.
 */
@Repository
public interface ImageRepository extends JpaRepository<Images, String> {

    Images findByOwner_Id(Long ownerId);
    Images findByTenant_Id(Long tenantId);
    Images findByImageUrl(String imageUrl);;
}
