package com.autoya.autoya_api.autoya.domain.model.events.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Response class containing information about a payment, including price, time, duration, start date, end date, image URL, and location.
 */
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
