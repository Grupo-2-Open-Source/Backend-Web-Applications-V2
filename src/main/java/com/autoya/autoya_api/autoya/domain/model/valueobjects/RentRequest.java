package com.autoya.autoya_api.autoya.domain.model.valueobjects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentRequest {
    public RentRequest(String vehiculeId, Long ownerId) {
        this.vehiculeuId = vehiculeId;
        this.ownerId = ownerId;
    }

    private String vehiculeuId;
    private Long ownerId;
    private Long tenantId;
}
