package com.autoya.autoya_api.autoya.domain.model.valueobjects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {
    private Long id;
    private String email;
    private String lastName;
    private String firstName;
    private String phoneNumber;
}
