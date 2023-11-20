package com.autoya.autoya_api.autoya.domain.model.events.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Response class containing information about rental requests.
 */
@Getter
@Setter
public class RequestsResponse {
    private String ownerName;
    private String vehicleBrand;
    private String vehicleModel;
    private String rentstatus;
    private String imageurl;
}
