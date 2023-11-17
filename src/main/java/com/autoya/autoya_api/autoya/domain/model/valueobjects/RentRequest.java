package com.autoya.autoya_api.autoya.domain.model.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentRequest {

    private String vehiculeId;
    private Long ownerId;
    private Long tenantId;
}
