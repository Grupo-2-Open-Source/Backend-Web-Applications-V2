package com.autoya.autoya_api.autoya.domain.model.events.requests;

import lombok.Getter;
import lombok.Setter;

/**
 * Request class for updating user profile information. Contains the user ID, email, last name, first name, and phone number.
 */
@Getter
@Setter
public class UpdateProfileRequest {
    private Long id;
    private String email;
    private String lastName;
    private String firstName;
    private String phoneNumber;
}
