package com.spring5.webflux.demo.controllers;

import com.spring5.webflux.demo.GTravelApplication;
import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.models.TravelOffer;
import com.spring5.webflux.demo.repositories.TravelOfferRepository;
import com.spring5.webflux.demo.repositories.TravelRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GTravelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("test")
public class TravelOfferControlllerTest {

    @Autowired
    WebTestClient client;

    @Autowired
    private TravelRepository travelRepository;

    @MockBean
    private TravelOfferRepository travelOfferRepository;

    private final String user = "user";
    private final String password = "password";

    @Test
    public void getAllTravelOffers_ShouldBeOk() {
        given(travelOfferRepository.findAll()).willReturn(
                Flux.just(
                        TravelOffer.builder()
                                .id("1")
                                .car(new BaseId("1"))
                                .travel(new BaseId("2"))
                                .freeSeats(1)
                                .price(350.0).build(),
                        TravelOffer.builder()
                                .id("2")
                                .car(new BaseId("2"))
                                .travel(new BaseId("3"))
                                .freeSeats(2)
                                .price(400.0).build()));

        client.mutate().filter(basicAuthentication(user, password)).build()
                .get().uri("/travel_offers").exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[1].id").isEqualTo("2")
                .jsonPath("$.[1].price").isEqualTo(400.0)
                .jsonPath("$.[1].freeSeats").isEqualTo(2);
    }

    @Test
    public void createTravelOfferByTravelType_ShouldBeOk() {

        TravelOffer travelOffer = TravelOffer.builder()
                .travel(new BaseId("1"))
                .car(new BaseId("1"))
                .freeSeats(2)
                .price(300.0).build();

        given(travelOfferRepository.save(any(TravelOffer.class)))
                .willReturn(Mono.just(travelOffer));

        client.mutate().filter(basicAuthentication(user, password)).build()
                .post().uri("/travel_offers/1/travel")
                .body(BodyInserters.fromObject(travelOffer))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.travel.id").isEqualTo("1")
                .jsonPath("$.freeSeats").isEqualTo(2)
                .jsonPath("$.price").isEqualTo(300.0);
    }

}