package com.autoya.autoya_api.autoya.domain.model.aggregate;

import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Tenant;
import com.autoya.autoya_api.autoya.domain.model.entities.Vehicule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents notifications in the system.
 * Stores information about notifications, including the owner, tenant, vehicle, and rental to whom they are related.
 */
@Entity
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner; // Relación con el propietario

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant; // Relación con el arrendatario

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicule vehicle; // Relación con el vehículo


    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rent rental;
}
