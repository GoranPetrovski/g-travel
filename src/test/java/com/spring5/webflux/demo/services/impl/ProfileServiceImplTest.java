package com.spring5.webflux.demo.services.impl;

import com.spring5.webflux.demo.models.Profile;
import com.spring5.webflux.demo.repositories.ProfileRepository;
import com.spring5.webflux.demo.services.ProfileService;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

//TODO check failing tests - all
@Log4j2
@DataMongoTest
@Import(ProfileServiceImpl.class)
@ActiveProfiles("test")
public class ProfileServiceImplTest {

    @Autowired
    private ProfileService service;

    @Autowired
    private ProfileRepository repository;

    @Test
    public void getAll() {
        Flux<Profile> saved = repository.saveAll(Flux.just(
                new Profile(null, "Josh"),
                new Profile(null, "Matt"),
                new Profile(null, "Line")));
        Flux<Profile> composite = service.all().thenMany(saved);
        Predicate<Profile> match = profile -> saved.any(saveItem -> saveItem.equals(profile)).block();

        //The StepVerifier is central to testing all things reactive.
        StepVerifier
                .create(composite)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .expectNextMatches(match)
                .verifyComplete();
    }

    @Test
    public void save(){
        Mono<Profile> profileMono = this.service.create("email@email.com");
        StepVerifier
                .create(profileMono)
                .expectNextMatches(saved -> StringUtils.hasText(saved.getId()))
                .verifyComplete();
    }

    @Test
    public void delete(){
        String test = "test";
        Mono<Profile> deleted = this.service
                .create(test)
                .flatMap(saved -> this.service.delete(saved.getId()));
        StepVerifier
                .create(deleted)
                .expectNextMatches(profile -> profile.getEmail().equalsIgnoreCase(test))
                .verifyComplete();
    }
}