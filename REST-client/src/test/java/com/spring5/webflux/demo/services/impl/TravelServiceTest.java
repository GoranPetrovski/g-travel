package com.spring5.webflux.demo.services.impl;

import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.models.Travel;
import com.spring5.webflux.demo.services.TravelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TravelServiceTest {

    @Mock
    TravelService travelService;

    private final Travel TRAVEL = Travel.builder().id("1")
            .fromLocation(new BaseId("1")) //city
            .toDestination(new BaseId("2")) //city
            .date(LocalDate.now().plusDays(1))
            .type(Travel.Type.TRAVELER)
            .user(new BaseId("1")).build();

    private final Flux<Travel> MOCK_TRAVELS = Flux.just(TRAVEL);

    private final Mono<Travel> travelMono = Mono.just(TRAVEL);

    private final Travel.Type SELECTED_TYPE = Travel.Type.TRAVELER;

    @BeforeEach
    void setMockOutput() {
        MockitoAnnotations.initMocks(this);
        when(travelService.all()).thenReturn(MOCK_TRAVELS);
        when(travelService.create(TRAVEL)).thenReturn(travelMono);
        when(travelService.getById("1")).thenReturn(travelMono);
        when(travelService.getByType(SELECTED_TYPE)).thenReturn(MOCK_TRAVELS.filter(t -> t.getType().equals(Travel.Type.TRAVELER)));
    }

    @Test
    public void getAll() {
        assertEquals(travelService.all(), MOCK_TRAVELS);
    }

    @Test
    public void create() {
        assertEquals(travelService.create(TRAVEL), travelMono);

        StepVerifier.create(travelService.create(TRAVEL))
                .expectNextMatches(saved -> StringUtils.hasText(saved.getId()))
                .verifyComplete();
    }

    @Test
    public void getOne() {
        StepVerifier.create(travelService.getById("1"))
                .expectNextMatches(travel -> {
                    assertEquals(travel.getDate(), LocalDate.now().plusDays(1));
                    return true;
                }).expectComplete().verify();
    }

    @Test
    public void getByType() {
        StepVerifier.create(travelService.getByType(SELECTED_TYPE))
                .expectNextMatches(travel -> {
                    assertEquals(travel.getType(), SELECTED_TYPE);
                    return true;
                }).expectComplete().verify();
    }
}
