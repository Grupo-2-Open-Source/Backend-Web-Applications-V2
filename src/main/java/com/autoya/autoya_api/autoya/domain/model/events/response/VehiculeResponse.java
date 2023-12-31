package com.autoya.autoya_api.autoya.domain.model.events.response;

import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.CarClass;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.CarTransmision;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.RentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

/**
 * Response class containing information about a vehicle.
 */
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
