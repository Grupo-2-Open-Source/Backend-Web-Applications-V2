package com.autoya.autoya_api.autoya.domain.model.valueobjects;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiculeResponseContract {
    private String id;
    private String brand;
    private String model;
    private String ownername;
    private Long ownerId;
    private String ownerphone;
    private String imageurl;
}
