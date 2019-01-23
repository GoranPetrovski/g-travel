package com.spring5.webflux.demo.controllers;

import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.models.TravelOffer;
import com.spring5.webflux.demo.services.TravelOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/travel_offers")
public class TravelOfferControlller {

    @Autowired
    private TravelOfferService travelOfferService;

    @GetMapping("")
    public Flux<TravelOffer> getAll() {
        return travelOfferService.getAll();
    }

    @GetMapping("/travel/{id}")
    public Mono<TravelOffer> getTravelById(@PathVariable("id") String id) {
        return travelOfferService.getByTravelId(new BaseId(id));
    }

    @PostMapping("/{id}/travel")
    public Mono<TravelOffer> createOfferByType(@PathVariable("id") String id, @RequestBody TravelOffer offer) {
        return travelOfferService.createOfferByType(id, offer);
    }
}
