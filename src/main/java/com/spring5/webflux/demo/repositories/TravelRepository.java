package com.spring5.webflux.demo.repositories;

import com.spring5.webflux.demo.models.Travel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TravelRepository extends ReactiveMongoRepository<Travel, String> {
}
