package com.kangwon.webflux1.service;

import com.kangwon.webflux1.client.PostClient;
import com.kangwon.webflux1.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostService {
    // webClient mvc server request이 필요
    //
    private final PostClient postClient;

    public Mono<PostResponse> getPostContent(Long id) {
        return postClient.getPost(id);
    }
}