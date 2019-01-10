package com.spring5.webflux.demo.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String id) {
        super("Post: " + id + " is not found. ");
    }
}
