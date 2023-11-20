package com.autoya.autoya_api.autoya.domain.model.events.requests;

import lombok.Getter;
import lombok.Setter;

/**
 * Request class for confirming a rental. Contains rental and tenant IDs.
 */
@Getter
@Setter
public class ConfirmRequest {
    private Long rentalId;
    private Long tenantId;
}
