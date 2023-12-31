package com.autoya.autoya_api.autoya.domain.model.aggregate;

import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Tenant;
import com.autoya.autoya_api.autoya.domain.model.entities.Vehicule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents criminal records associated with a tenant in the system.
 * Stores information about criminal records, linking them to a specific tenant.
 */
@Entity
@Getter
@Setter
public class CriminalRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
    private String pdf;
}
