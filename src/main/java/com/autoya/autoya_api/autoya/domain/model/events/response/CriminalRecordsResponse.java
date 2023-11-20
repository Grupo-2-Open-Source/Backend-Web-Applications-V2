package com.autoya.autoya_api.autoya.domain.model.events.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Response class containing the PDF link for criminal records related to a tenant.
 */
@Getter
@Setter
public class CriminalRecordsResponse {
    private String pdf;
}
