package com.autoya.autoya_api.autoya.domain.model.events.requests;

import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Tenant;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaintenanceRequest {
    private Long ownerId;
    private Long tenantId;
    private String problemType;
    private String description;
    private String imageUrl;
}
