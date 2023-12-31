package com.autoya.autoya_api.autoya.domain.model.events.requests;

import com.autoya.autoya_api.autoya.domain.model.valueobjects.CarClass;
import com.autoya.autoya_api.autoya.domain.model.valueobjects.CarTransmision;
import lombok.Getter;
import lombok.Setter;

/**
 * Request class for searching vehicles based on specified criteria. Contains parameters such as brand, model, weight,
 * car class, car transmission, location, price, rental time, and rental amount of time.
 */
@Getter
@Setter
public class VehiculeSearchRequest {

    private String brand;
    private String model;
    private Integer weight;
    private CarClass carClass;
    private CarTransmision carTransmission;
    private String location;
    private Integer price;
    private String time;
    private Long amoutthetime;

}
