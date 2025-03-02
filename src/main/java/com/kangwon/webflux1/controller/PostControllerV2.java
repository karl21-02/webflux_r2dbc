package com.kangwon.webflux1.controller;


import com.kangwon.webflux1.dto.PostCreateRequest;
import com.kangwon.webflux1.dto.PostResponse;
import com.kangwon.webflux1.dto.PostResponseV2;
import com.kangwon.webflux1.service.PostService;
import com.kangwon.webflux1.service.PostServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/posts")
public class PostControllerV2 {
    private final PostServiceV2 postServicev2;

    @PostMapping("")
    public Mono<PostResponseV2> createPost(@RequestBody PostCreateRequest request) {
        return postServicev2.create(request.getUserId(), request.getTitle(), request.getContent())
                .map(PostResponseV2::of);
    }

    @GetMapping("")
    public Flux<PostResponseV2> findAllPosts() {
        return postServicev2.findAll()
                .map(PostResponseV2::of);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PostResponseV2>> findPost(@PathVariable Long id) {
        return postServicev2.findById(id)
                .map(u -> ResponseEntity.ok(PostResponseV2.of(u)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletePost(@PathVariable Long id) {
        return postServicev2.deleteById(id).then(Mono.just(ResponseEntity.noContent().build()));
    }
}