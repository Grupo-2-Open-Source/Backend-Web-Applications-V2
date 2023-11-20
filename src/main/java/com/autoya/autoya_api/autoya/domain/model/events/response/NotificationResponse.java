package com.autoya.autoya_api.autoya.domain.model.events.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Response class containing information about a notification, including tenant name, vehicle brand, and vehicle model.
 */
@Getter
@Setter
public class NotificationResponse {

        private String tenantName;
        private String vehicleBrand;
        private String vehicleModel;
}
