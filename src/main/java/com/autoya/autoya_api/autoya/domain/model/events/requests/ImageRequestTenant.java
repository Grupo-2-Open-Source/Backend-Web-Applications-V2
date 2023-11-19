package com.autoya.autoya_api.autoya.domain.model.events.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageRequestTenant {
    private String imageUrl;
    private Long tenantId;
}
