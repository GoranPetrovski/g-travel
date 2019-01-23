package com.spring5.webflux.demo.repositories;

import com.spring5.webflux.demo.models.City;
import com.spring5.webflux.demo.models.Travel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface TravelRepository extends ReactiveMongoRepository<Travel, String> {
    Flux<Travel> findByType(Travel.Type type);

    Flux<Travel> findByTypeAndFromLocationAndToDestination(Travel.Type type, City location, City destination);

    Flux<Travel> findByTypeAndDateAfter(Travel.Type type, LocalDate date);
}
