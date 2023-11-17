package com.autoya.autoya_api.autoya.domain.model.valueobjects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmRequest {
    private Long rentalId;
    private Long tenantId;
}
