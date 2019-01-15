package com.spring5.webflux.demo.services;

import com.spring5.webflux.demo.models.Car;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CarService {

    Flux<Car> getAll();

    Mono<Car> create(Car car);
}
