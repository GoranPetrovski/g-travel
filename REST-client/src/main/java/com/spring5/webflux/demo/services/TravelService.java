package com.spring5.webflux.demo.services;

import com.spring5.webflux.demo.models.Travel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface TravelService {

    Flux<Travel> all();

    Mono<Travel> create(Travel travel);

    Mono<Travel> getById(String id);

    Flux<Travel> getByType(Travel.Type type);

    Flux<Travel> filterByTypeAndLocationAndDestination(Travel.Type type, String locationId, String destinationId);

    Flux<Travel> filterByDate(LocalDate date);
}
