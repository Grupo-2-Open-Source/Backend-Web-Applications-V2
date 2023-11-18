package com.autoya.autoya_api.autoya.domain.model.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentOwnerResponse {
    private Long rentId;
    private Long tenantId;
    private String nametenant;
    private String phonenumber;
    private String imageUrl;
    private String brand;
    private String model;
    private RentStatus rentStatus;
}
