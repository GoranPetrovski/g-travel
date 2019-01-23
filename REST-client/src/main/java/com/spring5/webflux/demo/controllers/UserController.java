package com.spring5.webflux.demo.controllers;

import com.spring5.webflux.demo.models.User;
import com.spring5.webflux.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{username}")
    public Mono<User> get(@PathVariable() String username) {
        return userRepository.findByUsername(username);
    }
}
