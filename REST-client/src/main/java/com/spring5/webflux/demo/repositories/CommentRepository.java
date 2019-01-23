package com.spring5.webflux.demo.repositories;

import com.spring5.webflux.demo.helpers.PostId;
import com.spring5.webflux.demo.models.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Flux<Comment> findByPost(PostId id);
}
