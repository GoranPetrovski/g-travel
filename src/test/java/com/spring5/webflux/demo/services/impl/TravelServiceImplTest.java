package com.spring5.webflux.demo.services.impl;

import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.models.Travel;
import com.spring5.webflux.demo.services.TravelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TravelServiceImplTest {

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

    @BeforeEach
    void setMockOutput() {
        MockitoAnnotations.initMocks(this);
        when(travelService.all()).thenReturn(MOCK_TRAVELS);
        when(travelService.create(TRAVEL)).thenReturn(travelMono);
    }

    @Test
    public void getAll() {
        assertEquals(travelService.all(), MOCK_TRAVELS);
    }

    @Test
    public void create() {
        assertEquals(travelService.create(TRAVEL), travelMono);
    }
}