package com.autoya.autoya_api.autoya.domain.model.events.requests;

import lombok.Getter;
import lombok.Setter;

/**
 * Request class for submitting an image associated with a tenant. Contains the image URL and the tenant ID.
 */
@Getter
@Setter
public class ImageRequestTenant {
    private String imageUrl;
    private Long tenantId;
}
