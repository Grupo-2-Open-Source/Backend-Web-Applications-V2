package com.autoya.autoya_api.shared.domain.model.entities;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import java.util.Date;

/**
 * Base class representing an auditable model with common audit fields.
 * This class is annotated with {@link MappedSuperclass} to indicate that its fields
 * should be mapped to the fields of subclasses in the database.
 * It includes audit fields such as creation and last modification timestamps.
 */
@Data
@MappedSuperclass
public class AuditableModel {
    @CreatedDate
    private Date createdAt;
    @LastModifiedBy
    private Date updatedAt;
}
