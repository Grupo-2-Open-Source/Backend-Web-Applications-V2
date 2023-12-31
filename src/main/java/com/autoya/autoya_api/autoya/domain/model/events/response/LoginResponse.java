package com.autoya.autoya_api.autoya.domain.model.events.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Response class containing the result message and user ID after a login attempt.
 */
@Getter
@Setter
public class LoginResponse {
    public LoginResponse(String messa,Long id) {
        this.messa=messa;
        this.id = id;
    }
    private String messa;
    private Long id;
}
