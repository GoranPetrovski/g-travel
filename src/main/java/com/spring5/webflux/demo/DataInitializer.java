package com.spring5.webflux.demo;

import com.spring5.webflux.demo.repositories.ProfileRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Log4j2
@Component
@Profile("demo")
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ProfileRepository profileRepository;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        profileRepository
                .deleteAll()
                .thenMany(
                        Flux
                                .just("A", "B", "C", "D", "E")
                                .map(name -> new com.spring5.webflux.demo.models.Profile(UUID.randomUUID().toString(), name + "@email.com"))
                                .flatMap(profileRepository::save)
                )
                .thenMany(profileRepository.findAll())
                .subscribe(profile -> log.info("Saving " + profile.toString()));
    }
}
