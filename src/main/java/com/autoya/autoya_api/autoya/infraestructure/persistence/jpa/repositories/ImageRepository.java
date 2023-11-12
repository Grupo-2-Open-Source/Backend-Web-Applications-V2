package com.autoya.autoya_api.autoya.infraestructure.persistence.jpa.repositories;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Images, Long> {

    Images findByOwner_Id(Long ownerId);
    Images findByTenant_Id(Long tenantId);
}
