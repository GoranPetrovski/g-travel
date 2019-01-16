package com.spring5.webflux.demo.services.impl;

import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.models.Travel;
import com.spring5.webflux.demo.models.TravelRequest;
import com.spring5.webflux.demo.repositories.TravelRequestRepository;
import com.spring5.webflux.demo.services.TravelRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TravelRequestServiceImpl implements TravelRequestService {

    @Autowired
    private TravelRequestRepository travelRequestRepository;

    @Override
    public Flux<TravelRequest> findAll() {
        return travelRequestRepository.findAll();
    }

    @Override
    public Mono<Travel> findByTravel(BaseId id) {
        return travelRequestRepository.findByTravel(id);
    }

    @Override
    public Mono<TravelRequest> createRequestByType(String travelId, TravelRequest request) {
        request.setTravel(new BaseId(travelId));
        return travelRequestRepository.save(request);
    }
}
