package com.spring5.webflux.demo.services;

import com.spring5.webflux.demo.models.City;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CityService {

    Mono<City> getByName(String name);

    Flux<City> getAll();
}
