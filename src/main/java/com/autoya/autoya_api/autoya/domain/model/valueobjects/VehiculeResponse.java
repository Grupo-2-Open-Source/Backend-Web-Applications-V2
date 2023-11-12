package com.autoya.autoya_api.autoya.domain.model.valueobjects;

import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiculeResponse {
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
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private RentStatus rentStatus;
    private String location;
    private Integer price;
    private String time;
    private Long amoutthetime;
    private Long ownerId;
    private String ownername;
    private String ownerphone;

}
