package com.autoya.autoya_api.autoya.domain.model.valueobjects;

import lombok.Getter;
import lombok.Setter;

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
