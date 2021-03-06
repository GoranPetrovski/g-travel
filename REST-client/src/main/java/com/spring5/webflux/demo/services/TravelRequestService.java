package com.spring5.webflux.demo.services;

import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.models.TravelRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TravelRequestService {

    Flux<TravelRequest> findAll();

    Mono<TravelRequest> findByTravel(BaseId id);

    Mono<TravelRequest> createRequestByType(String travelId, TravelRequest request);
}
