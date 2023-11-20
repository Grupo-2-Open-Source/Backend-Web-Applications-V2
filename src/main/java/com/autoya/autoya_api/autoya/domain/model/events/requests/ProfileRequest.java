package com.autoya.autoya_api.autoya.domain.model.events.requests;

import lombok.Getter;
import lombok.Setter;

/**
 * Request class for retrieving a user's profile information. Contains the user's ID.
 */
@Getter
@Setter
public class ProfileRequest {
    private Long id;
}
