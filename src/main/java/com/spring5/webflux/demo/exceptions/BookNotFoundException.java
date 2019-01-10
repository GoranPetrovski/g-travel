package com.spring5.webflux.demo.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String id) {
        super("Book: " + id + " is not found. ");
    }
}
