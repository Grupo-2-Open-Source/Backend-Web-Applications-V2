package com.autoya.autoya_api.autoya.domain.model.events.requests;

import com.autoya.autoya_api.autoya.domain.model.entities.Owner;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.CarClass;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.CarTransmision;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.RentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

/**
 * Request class for registering a new vehicle. Contains details such as brand, model, maximum velocity, fuel consumption,
 * dimensions, weight, car class, car transmission, rent status, location, price, rental time, rental amount of time,
 * owner ID, and image URL.
 */
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
