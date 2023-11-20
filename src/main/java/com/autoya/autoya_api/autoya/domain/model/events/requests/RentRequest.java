package com.autoya.autoya_api.autoya.domain.model.events.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request class for initiating a vehicle rental. Contains the vehicle ID, owner ID, and tenant ID.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentRequest {

    private String vehiculeId;
    private Long ownerId;
    private Long tenantId;
}
