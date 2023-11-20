package com.autoya.autoya_api.autoya.domain.model.events.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Response class containing user profile information, including ID, email, password, last name, first name, birth date, and phone number.
 */
@Getter
@Setter
public class ProfileResponse {
    private Long id;
    private String email;
    private String password;
    private String lastName;
    private String firstName;
    private String birthDate;
    private String phoneNumber;
}
