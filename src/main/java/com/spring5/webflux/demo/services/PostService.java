package com.spring5.webflux.demo.services;

import com.spring5.webflux.demo.helpers.Count;
import com.spring5.webflux.demo.models.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {
    Flux<Post> getAll();

    Mono<Count> count();

    Mono<Post> create(Post post);

    Mono<Post> getById(String id);

    Mono<Post> update(String id, Post post);
}
