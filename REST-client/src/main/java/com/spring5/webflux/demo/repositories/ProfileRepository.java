package com.spring5.webflux.demo.repositories;

import com.spring5.webflux.demo.models.Profile;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProfileRepository extends ReactiveMongoRepository<Profile, String> {
}
