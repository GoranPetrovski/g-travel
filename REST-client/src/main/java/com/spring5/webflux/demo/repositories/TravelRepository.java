package com.spring5.webflux.demo.repositories;

import com.spring5.webflux.demo.models.Travel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface TravelRepository extends ReactiveMongoRepository<Travel, String> {
    Flux<Travel> findByType(Travel.Type type);

    Flux<Travel> findByTypeAndFromLocationIdAndToDestinationId(Travel.Type type, String location, String destination);

    Flux<Travel> findByDate(LocalDate date);
}
