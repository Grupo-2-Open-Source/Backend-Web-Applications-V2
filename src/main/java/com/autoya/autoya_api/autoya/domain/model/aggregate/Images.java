package com.autoya.autoya_api.autoya.domain.model.aggregate;

import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Tenant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private Owner owner; // Relación con el propietario

    @OneToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant; // Relación con el arrendatario

    private String ImageUrl;


}
