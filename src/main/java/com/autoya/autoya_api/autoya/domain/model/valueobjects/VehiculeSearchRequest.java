package com.autoya.autoya_api.autoya.domain.model.valueobjects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiculeSearchRequest {

    private String brand;
    private String model;
    private Integer maxVelocity;
    private Integer fuelConsumption;
    private String dimensions;
    private Integer weight;
    private CarClass carClass;
    private CarTransmision carTransmission;
    private String location;
    private Integer price;
    private String time;
    private Long amoutthetime;

}
