package com.spring5.webflux.demo.repositories;

import com.spring5.webflux.demo.models.City;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CityRepository extends ReactiveMongoRepository<City, String> {
    Mono<City> findByName(String name);
}
