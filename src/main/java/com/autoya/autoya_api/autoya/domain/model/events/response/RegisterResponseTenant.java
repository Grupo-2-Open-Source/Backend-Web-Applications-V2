package com.autoya.autoya_api.autoya.domain.model.events.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Response class containing the ID information for a successful registration of a tenant.
 */
@Getter
@Setter
@AllArgsConstructor
public class RegisterResponseTenant {
    private Long id;
}
