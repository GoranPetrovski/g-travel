package com.spring5.webflux.demo.services.impl;

import com.spring5.webflux.demo.exceptions.PostNotFoundException;
import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.helpers.Count;
import com.spring5.webflux.demo.helpers.PostId;
import com.spring5.webflux.demo.models.Comment;
import com.spring5.webflux.demo.models.Post;
import com.spring5.webflux.demo.repositories.CommentRepository;
import com.spring5.webflux.demo.repositories.PostRepository;
import com.spring5.webflux.demo.services.PostService;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import static java.util.Comparator.comparing;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Flux<Post> getAll(String q, long page, long size) {
        return this.filterPublishedPostsByKeyword(q).sort(comparing(Post::getCreatedDate).reversed())
                .skip(page * size).take(size);
    }

    @Override
    public Mono<Count> count(String q) {
        return this.filterPublishedPostsByKeyword(q).count().log().map(Count::new);
    }

    @Override
    public Mono<Post> createForTravel(String travelId, Post post) {
        return postRepository.save(Post.builder()
                .content(post.getContent())
                .travel(new BaseId(travelId))
                .title(post.getTitle())
                .createdDate(post.getCreatedDate())
                .status(post.getStatus())
                .build());
    }

    @Override
    public Mono<Post> getById(String id) {
        return postRepository.findById(id)
                .switchIfEmpty(Mono.error(new PostNotFoundException(id)));
    }

    @Override
    public Mono<Post> update(String id, Post post) {
        return postRepository.findById(id)
                .switchIfEmpty(Mono.error(new PostNotFoundException(id)))
                .map(p -> {
                    p.setTitle(post.getTitle());
                    p.setContent(post.getContent());
                    return p;
                }).flatMap(postRepository::save);
    }

    @Override
    public Mono<Void> delete(String id) {
        return postRepository.findById(id)
                .switchIfEmpty(Mono.error(new PostNotFoundException(id)))
                .flatMap(postRepository::delete);
    }

    @Override
    public Flux<Comment> getCommentOf(String id) {
        return commentRepository.findByPost(new PostId(id));
    }

    @Override
    public Mono<Count> getCommentsCountOf(String id) {
        return commentRepository.findByPost(new PostId(id)).count().log().map(Count::new);
    }

    @Override
    public Mono<Comment> createCommentsOf(String postId, Comment comment) {
        Comment c = Comment.builder().post(new PostId(postId)).content(comment.getContent()).build();
        return commentRepository.save(c);

    }

    private Flux<Post> filterPublishedPostsByKeyword(String q) {
        return postRepository.findAll()
                .filter(p -> Post.Status.PUBLISHED == p.getStatus())
                .filter(p -> Optional.ofNullable(q)
                        .map(key -> p.getTitle().contains(key) || p.getContent().contains(key))
                        .orElse(true)

                );
    }

}
