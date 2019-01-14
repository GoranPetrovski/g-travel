package com.spring5.webflux.demo.web;

import com.spring5.webflux.demo.exceptions.PostNotFoundException;
import com.spring5.webflux.demo.models.Post;
import com.spring5.webflux.demo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;


@Component
public class PostHandler {
    @Autowired
    private PostService postService;

    public Mono<ServerResponse> getAll(ServerRequest request) {

        String q = request.queryParam("q").isPresent() ?
                request.queryParam("q").get() : "";

        long page = request.queryParam("page").isPresent() ?
                Long.parseLong(request.queryParam("page").get()) : 10;

        long size = request.queryParam("size").isPresent() ?
                Long.parseLong(request.queryParam("size").get()) : 10;

        return ok()
                .contentType(APPLICATION_JSON)
                .body(postService.getAll(q, page, size), Post.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");

        return ok()
                .contentType(APPLICATION_JSON)
                .body(postService.getById(id), Post.class)
                .switchIfEmpty(Mono.error(new PostNotFoundException(id)));
    }
}
