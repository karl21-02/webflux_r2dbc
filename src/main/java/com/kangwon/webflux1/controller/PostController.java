package com.kangwon.webflux1.controller;


import com.kangwon.webflux1.dto.PostResponse;
import com.kangwon.webflux1.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public Mono<PostResponse> getPostContent(@PathVariable Long id) {
        return postService.getPostContent(id)
                .onErrorResume(e -> Mono.just(new PostResponse(id.toString(), "Fallbaxk data %d".formatted(id))));
    }

    @GetMapping("/search")
    public Flux<PostResponse> getMultiplePostContent(@RequestParam(name = "ids") List<Long> idList) {
        return postService.getParallelMultiplePostContent(idList);
    }

}