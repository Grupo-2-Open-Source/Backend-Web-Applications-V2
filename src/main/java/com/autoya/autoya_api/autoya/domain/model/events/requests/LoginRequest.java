package com.autoya.autoya_api.autoya.domain.model.events.requests;

import lombok.Getter;
import lombok.Setter;

/**
 * Request class for user login. Contains the user's email and password.
 */
@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;

}
