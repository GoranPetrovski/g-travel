package com.spring5.webflux.demo.web;

import com.spring5.webflux.demo.models.Profile;
import com.spring5.webflux.demo.services.ProfileService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ProfileHandler {

    @Autowired
    private ProfileService profileService;

    Mono<ServerResponse> getById(ServerRequest request) {
        return defaultReadResponse(profileService.get(pathId(request)));
    }

    Mono<ServerResponse> all(ServerRequest request) {
        return defaultReadResponse(profileService.all());
    }

    Mono<ServerResponse> deleteById(ServerRequest request) {
        return defaultReadResponse(profileService.delete(pathId(request)));
    }

    Mono<ServerResponse> updateById(ServerRequest request) {
        Flux<Profile> id = request.bodyToFlux(Profile.class)
                .flatMap(profile -> profileService.update(pathId(request), profile.getEmail()));
        return defaultReadResponse(id);
    }

    private static Mono<ServerResponse> defaultReadResponse(Publisher<Profile> profiles) {
        return ok()
                .contentType(APPLICATION_JSON_UTF8)
                .body(profiles, Profile.class);
    }

    private String pathId(ServerRequest request) {
        return request.pathVariable("id");
    }
}
