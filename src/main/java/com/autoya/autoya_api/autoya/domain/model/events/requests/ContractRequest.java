package com.autoya.autoya_api.autoya.domain.model.events.requests;

import lombok.Getter;
import lombok.Setter;

/**
 * Request class for creating a contract. Contains vehicle ID, owner ID, and the PDF content.
 */
@Getter
@Setter
public class ContractRequest{
        private String vehicleId;
        private Long ownerId;
        private String pdf;
}
