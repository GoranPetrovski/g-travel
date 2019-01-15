package com.spring5.webflux.demo.services.impl;

import com.spring5.webflux.demo.models.City;
import com.spring5.webflux.demo.repositories.CityRepository;
import com.spring5.webflux.demo.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public Mono<City> getByName(String name) {
        return cityRepository.findByName(name);
    }

    @Override
    public Flux<City> getAll() {
        return cityRepository.findAll();
    }
}
