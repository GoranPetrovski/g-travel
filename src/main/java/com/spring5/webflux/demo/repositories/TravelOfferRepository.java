package com.spring5.webflux.demo.repositories;

import com.spring5.webflux.demo.models.TravelOffer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TravelOfferRepository extends ReactiveMongoRepository<TravelOffer, String> {
}
