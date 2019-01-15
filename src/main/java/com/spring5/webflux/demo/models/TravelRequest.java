package com.spring5.webflux.demo.models;

import com.spring5.webflux.demo.helpers.BaseId;

public class TravelRequest {
    private BaseId travel;

    private Integer requiredSeats = 1; // default 1
}
