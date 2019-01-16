package com.spring5.webflux.demo.services.impl;

import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.models.Travel;
import com.spring5.webflux.demo.models.TravelOffer;
import com.spring5.webflux.demo.repositories.TravelOfferRepository;
import com.spring5.webflux.demo.services.TravelOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TravelOfferServiceImpl implements TravelOfferService {

    @Autowired
    private TravelOfferRepository travelOfferRepository;

    @Override
    public Mono<Travel> getByTravelId(BaseId id) {
        return travelOfferRepository.findByTravel(id);
    }

    @Override
    public Flux<TravelOffer> getAll() {
        return travelOfferRepository.findAll();
    }

    @Override
    public Mono<TravelOffer> createOfferByType(String travelId, TravelOffer offer) {
        offer.setTravel(new BaseId(travelId));
        return travelOfferRepository.save(offer);
    }
}
