package com.kangwon.webflux1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
public class Webflux1Application {

    public static void main(String[] args) {
        SpringApplication.run(Webflux1Application.class, args);
    }

    @GetMapping("/posts/{id}")
    public Map<String, String> getPosts(@PathVariable Long id) throws Exception {
        Thread.sleep(3000);
        if(id > 10L) {
            throw new Exception("Too Long");
        }
        return Map.of("id", id.toString(), "content", "Posts content is %d".formatted(id));
    }
}