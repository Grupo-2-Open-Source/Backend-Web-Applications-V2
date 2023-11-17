package com.autoya.autoya_api.autoya.domain.model.aggregate;

import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.entities.Vehicule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicule vehicle;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    private String pdf;
}
