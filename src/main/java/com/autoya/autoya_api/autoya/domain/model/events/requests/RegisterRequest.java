package com.autoya.autoya_api.autoya.domain.model.events.requests;

import lombok.Getter;
import lombok.Setter;

/**
 * Request class for user registration. Contains user details such as email, password, last name,
 * first name, birth date, and phone number.
 */
@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String password;
    private String lastName;
    private String firstName;
    private String birthDate;
    private String phoneNumber;
}
