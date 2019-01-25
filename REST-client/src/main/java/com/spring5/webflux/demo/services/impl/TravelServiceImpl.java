package com.spring5.webflux.demo.services.impl;

import com.spring5.webflux.demo.exceptions.PostNotFoundException;
import com.spring5.webflux.demo.models.Travel;
import com.spring5.webflux.demo.repositories.TravelRepository;
import com.spring5.webflux.demo.services.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Optional;

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

    @Override
    public Flux<Travel> getByType(Travel.Type type) {
        return travelRepository.findByType(type);
    }

    @Override
    public Flux<Travel> filterByTypeAndLocationAndDestination(Travel.Type type, String locationId, String destinationId) {
        return travelRepository.findByTypeAndFromLocationIdAndToDestinationId(type, locationId, destinationId);
    }

    @Override
    public Flux<Travel> filterByDate(LocalDate date) {
        return travelRepository.findAll().filter(t -> Optional.ofNullable(date)
                .map(d -> t.getDate().equals(date)).orElse(true));
    }

    private Flux<Travel> filterTravels(Flux<Travel> travels, String from, String to) {
        return travels.filter(t -> Travel.Type.TRAVELER == t.getType())
                .filter(t -> Optional.ofNullable(from)
                        .map(location -> t.getFromLocation().equals(from))
                        .orElse(true))
                .filter(t -> Optional.ofNullable(to)
                        .map(destination -> t.getToDestination().equals(to)).orElse(true));
    }
}
