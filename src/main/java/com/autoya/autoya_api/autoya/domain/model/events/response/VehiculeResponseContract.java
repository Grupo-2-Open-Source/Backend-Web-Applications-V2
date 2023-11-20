package com.autoya.autoya_api.autoya.domain.model.events.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

/**
 * Response class containing information about a vehicle and its owner for contract page.
 */
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
