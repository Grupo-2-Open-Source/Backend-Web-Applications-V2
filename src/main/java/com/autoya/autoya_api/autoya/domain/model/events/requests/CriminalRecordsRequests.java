package com.autoya.autoya_api.autoya.domain.model.events.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriminalRecordsRequests {
    private String pdfUrl;
    private Long tenantId;
}
