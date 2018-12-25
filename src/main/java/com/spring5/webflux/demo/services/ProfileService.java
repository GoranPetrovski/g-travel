package com.spring5.webflux.demo.services;

import com.spring5.webflux.demo.models.Profile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProfileService {

    Flux<Profile> all();

    Mono<Profile> get(String id);

    Mono<Profile> update(String id, String email);

    Mono<Profile> delete(String id);

    Mono<Profile> create(String email);
}
