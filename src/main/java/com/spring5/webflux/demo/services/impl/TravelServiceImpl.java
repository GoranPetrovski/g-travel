package com.spring5.webflux.demo.services.impl;

import com.spring5.webflux.demo.exceptions.PostNotFoundException;
import com.spring5.webflux.demo.models.Travel;
import com.spring5.webflux.demo.repositories.TravelRepository;
import com.spring5.webflux.demo.services.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TravelServiceImpl implements TravelService {

    @Autowired
    private TravelRepository travelRepository;

    @Override
    public Flux<Travel> all() {
        return travelRepository.findAll();
    }

    @Override
    public Mono<Travel> create(Travel travel) {
        return travelRepository.save(travel);
    }

    @Override
    public Mono<Travel> getById(String id) {
        return travelRepository.findById(id)
                .switchIfEmpty(Mono.error(new PostNotFoundException(id)));
    }
}
