package com.autoya.autoya_api.autoya.domain.model.entities;

import com.autoya.autoya_api.autoya.domain.model.aggregate.Contract;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.CarClass;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.CarTransmision;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.RentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * Entity class representing a vehicle.
 */
@Entity
@Data
@Getter
@Setter
public class Vehicule {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "vehicule_id", unique = true)
    private String id;

    private String brand;
    private String model;
    private Integer maxVelocity;
    private Integer fuelConsumption;
    private String dimensions;
    private Integer weight;

    @Enumerated(EnumType.STRING)
    private CarClass carClass;

    @Enumerated(EnumType.STRING)
    private CarTransmision carTransmission;

    private String location;
    private Integer price;

    private String time;
    private Long amoutthetime;

    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private RentStatus rentStatus;
    public Vehicule() {
        this.rentStatus = RentStatus.WAITING;
    }



    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @OneToOne(mappedBy = "vehicle")
    private Contract contract;

}
