package com.autoya.autoya_api.autoya.domain.model.events.response;

import com.autoya.autoya_api.autoya.domain.model.valueobjects.RentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response class containing information about a rental for the owner.
 */
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
