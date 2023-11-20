package com.autoya.autoya_api.autoya.domain.model.events.requests;

import lombok.Getter;
import lombok.Setter;

/**
 * Request class for submitting an image associated with an owner. Contains the image URL and the owner ID.
 */
@Getter
@Setter
public class ImageRequestOwner {
    private String imageUrl;
    private Long ownerId;
}
