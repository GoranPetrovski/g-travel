package com.spring5.webflux.demo.repositories;

import com.spring5.webflux.demo.models.City;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CityRepository extends ReactiveMongoRepository<City, String> {
}
