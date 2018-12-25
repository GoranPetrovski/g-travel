package com.spring5.webflux.demo.services.impl;

import com.spring5.webflux.demo.events.ProfileCreatedEvent;
import com.spring5.webflux.demo.models.Profile;
import com.spring5.webflux.demo.repositories.ProfileRepository;
import com.spring5.webflux.demo.services.ProfileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Flux<Profile> all() {
        return profileRepository.findAll();
    }

    @Override
    public Mono<Profile> get(String id) {
        return profileRepository.findById(id);
    }

    @Override
    public Mono<Profile> update(String id, String email) {
        return profileRepository
                .findById(id)
                .map(p -> new Profile(p.getId(), email))
                .flatMap(profileRepository::save);
    }

    @Override
    public Mono<Profile> delete(String id) {
        return profileRepository
                .findById(id)
                .flatMap(p -> profileRepository.deleteById(p.getId()).thenReturn(p));
    }

    @Override
    public Mono<Profile> create(String email) {
        return profileRepository
                .save(new Profile(null, email))
                .doOnSuccess(profile -> this.publisher.publishEvent(new ProfileCreatedEvent(profile)));
    }
}
