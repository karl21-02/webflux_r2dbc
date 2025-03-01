package com.kangwon.webflux1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class SampleController {

    // reactor
    // publisher <--> subscriber

    @GetMapping("sample/hello")
    public Mono<String> getHello() {
        return Mono.just("Hello rest controller with webflux");
    }
}
