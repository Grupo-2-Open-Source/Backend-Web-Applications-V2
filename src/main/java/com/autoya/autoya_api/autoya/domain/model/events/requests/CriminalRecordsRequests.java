package com.autoya.autoya_api.autoya.domain.model.events.requests;

import lombok.Getter;
import lombok.Setter;

/**
 * Request class for submitting criminal records. Contains the URL of the PDF file and the tenant ID.
 */
@Getter
@Setter
public class CriminalRecordsRequests {
    private String pdfUrl;
    private Long tenantId;
}
