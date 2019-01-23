package com.spring5.webflux.demo.services;

import com.spring5.webflux.demo.helpers.Count;
import com.spring5.webflux.demo.models.Comment;
import com.spring5.webflux.demo.models.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {

    Flux<Post> getAll(String q, long page, long size);

    Mono<Count> count(String q);

    Mono<Post> createForTravel(String travelId, Post post);

    Mono<Post> getById(String id);

    Mono<Post> update(String id, Post post);

    Mono<Void> delete(String id);

    Flux<Comment> getCommentOf(String id);

    Mono<Count> getCommentsCountOf(String postId);

    Mono<Comment> createCommentsOf(String postId, Comment comment);
}
