package com.spring5.webflux.demo.repositories;

import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.models.TravelRequest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TravelRequestRepository extends ReactiveMongoRepository<TravelRequest, String> {
    Mono<TravelRequest> findByTravel(BaseId id);
}
