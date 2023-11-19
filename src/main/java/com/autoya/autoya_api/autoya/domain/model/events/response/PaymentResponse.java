package com.autoya.autoya_api.autoya.domain.model.events.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PaymentResponse {
    private Integer price;
    private String time;
    private Long amountthetime;
    private Date startDate;
    private Date endDate;
    private String imageurl;
    private String location;
}
