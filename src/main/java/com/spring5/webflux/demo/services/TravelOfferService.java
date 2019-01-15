package com.spring5.webflux.demo.services;

import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.models.Travel;
import com.spring5.webflux.demo.models.TravelOffer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TravelOfferService {
    Mono<Travel> getByTravelId(BaseId id);

    Flux<TravelOffer> getAll();
}
