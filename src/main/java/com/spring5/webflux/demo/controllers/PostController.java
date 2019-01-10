package com.spring5.webflux.demo.controllers;

import com.spring5.webflux.demo.helpers.Count;
import com.spring5.webflux.demo.models.Comment;
import com.spring5.webflux.demo.models.Post;
import com.spring5.webflux.demo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    private Mono<Post> postMono;

    @GetMapping("")
    public Flux<Post> all(@RequestParam(value = "q", required = false) String q,
                          @RequestParam(value = "page", defaultValue = "0") long page,
                          @RequestParam(value = "size", defaultValue = "10") long size) {
        return postService.getAll(q, page, size);
    }

    @GetMapping(value = "/count")
    public Mono<Count> count(@RequestParam(value = "q", required = false) String q) {
        return postService.count(q);
    }

    @PostMapping("")
    public Mono<Post> create(@RequestBody @Valid Post post) {
        return postService.create(post);
    }

    @GetMapping("/{id}")
    public Mono<Post> getById(@PathVariable("id") String id) {
        return postService.getById(id);
    }

    @PutMapping("/{id}")
    public Mono<Post> update(@PathVariable("id") String id, @RequestBody @Valid Post post) {
        return postService.update(id, post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> delete(@PathVariable("id") String id) {
        return postService.delete(id);
    }

    @GetMapping("/{id}/comments")
    public Flux<Comment> getCommentsOf(@PathVariable("id") String id) {
        return postService.getCommentOf(id);
    }

    @GetMapping("/{id}/comments/count")
    public Mono<Count> getCommentsCountOf(@PathVariable("id") String id) {
        return postService.getCommentsCountOf(id);
    }

    @PostMapping("/{id}/comments")
    public Mono<Comment> createCommentsOf(@PathVariable("id") String id, @RequestBody @Valid Comment comment) {
        return postService.createCommentsOf(id, comment);
    }
}
