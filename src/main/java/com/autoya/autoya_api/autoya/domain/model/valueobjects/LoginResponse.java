package com.autoya.autoya_api.autoya.domain.model.valueobjects;

import lombok.Getter;
import lombok.Setter;

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
