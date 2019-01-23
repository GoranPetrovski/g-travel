package com.spring5.webflux.demo.repositories;

import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.models.TravelOffer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TravelOfferRepository extends ReactiveMongoRepository<TravelOffer, String> {
    Mono<TravelOffer> findByTravel(BaseId id);
}
