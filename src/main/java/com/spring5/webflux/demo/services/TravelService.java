package com.spring5.webflux.demo.services;

import com.spring5.webflux.demo.models.Travel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TravelService {

    Flux<Travel> all();

    Mono<Travel> create(Travel travel);

    Mono<Travel> getById(String id);
}
