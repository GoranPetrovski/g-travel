package com.spring5.webflux.demo.controllers;

import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.models.Travel;
import com.spring5.webflux.demo.models.TravelRequest;
import com.spring5.webflux.demo.services.TravelRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/travel_requests")
public class TravelRequestConroller {

    @Autowired
    private TravelRequestService travelRequestService;

    @GetMapping("")
    public Flux<TravelRequest> getAll() {
        return travelRequestService.findAll();
    }

    @GetMapping("/travel/{id}")
    public Mono<Travel> getTravelById(@PathVariable("id") String id) {
        return travelRequestService.findByTravel(new BaseId(id));
    }
}
