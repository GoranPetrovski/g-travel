package com.spring5.webflux.demo.repositories;

import com.spring5.webflux.demo.models.Post;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PostRepository extends ReactiveMongoRepository<Post, String> {
}
