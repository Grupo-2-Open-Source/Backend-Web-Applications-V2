package com.autoya.autoya_api.autoya.domain.model.events.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractRequest{
        private String vehicleId;
        private Long ownerId;
        private String pdf;
}
