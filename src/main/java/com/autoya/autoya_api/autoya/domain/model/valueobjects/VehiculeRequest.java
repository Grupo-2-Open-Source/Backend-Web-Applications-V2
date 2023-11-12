package com.autoya.autoya_api.autoya.domain.model.valueobjects;

import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiculeRequest {
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
    @Enumerated(EnumType.STRING)
    private RentStatus rentStatus =RentStatus.WAITING;
    private String location;
    private Integer price;
    private String time;
    private Long amoutthetime;
    private Long ownerId;
    private String imageUrl;

}
