package com.spring5.webflux.demo.web;

import com.spring5.webflux.demo.GTravelApplication;
import com.spring5.webflux.demo.models.Profile;
import com.spring5.webflux.demo.repositories.ProfileRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.BDDMockito.given;

@Log4j2
//@WebFluxTest
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GTravelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProfileEndpointConfigurationTest {

    @Autowired
    private ProfileRouter profileRouter;

    @MockBean
    private ProfileRepository profileRepository;

    @Test
    public void getAll() {
        log.info("running " + this.getClass().getName());
        WebTestClient client = WebTestClient
                .bindToRouterFunction(profileRouter.getProfiles())
                .build();


        given(profileRepository.findAll())
                .willReturn(Flux.just(
                        new Profile("1", "A"),
                        new Profile("2", "B")
                ));

        Mockito.when(profileRepository.findAll())
                .thenReturn(Flux.just(
                        new Profile("1", "A"),
                        new Profile("2", "B")
                ));

        client.get()
                .uri("/profiles")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo("1")
                .jsonPath("$.[0].email").isEqualTo("A")
                .jsonPath("$.[1].id").isEqualTo("2")
                .jsonPath("$.[1].email").isEqualTo("B");

    }
}