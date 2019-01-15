package com.spring5.webflux.demo.repositories;

import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.models.Travel;
import com.spring5.webflux.demo.models.TravelRequest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TravelRequestRepository extends ReactiveMongoRepository<TravelRequest, String> {
    Mono<Travel> findByTravel(BaseId id);
}
