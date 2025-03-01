package com.kangwon.webflux1.controller;

import com.kangwon.webflux1.dto.UserCreateRequest;
import com.kangwon.webflux1.dto.UserResponse;
import com.kangwon.webflux1.repository.User;
import com.kangwon.webflux1.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// UserService를 직접 등록
class UserServiceTestConfig {
    @Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }
}

// @MockBean 대신 @Import 사용
@WebFluxTest(controllers = UserController.class)
@Import(UserServiceTestConfig.class)
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserService userService;

    @Test
    void createUser() {
        when(userService.create("greg", "greg@afsdkfj.com")).thenReturn(
                Mono.just(new User(1L, "greg", "greg@afsdkfj.com", LocalDateTime.now(), LocalDateTime.now()))
        );
        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateRequest("greg", "greg@afsdkfj.com"))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals("greg", res.getName());
                    assertEquals("greg@afsdkfj.com", res.getEmail());
                });
    }

    @Test
    void findAllUsers() {
        when(userService.findAll()).thenReturn(
                Flux.just(
                        new User(1L, "greg", "greg@afsdkfj.com", LocalDateTime.now(), LocalDateTime.now()),
                        new User(2L, "alice", "alice@mail.com", LocalDateTime.now(), LocalDateTime.now()),
                        new User(3L, "bob", "bob@mail.com", LocalDateTime.now(), LocalDateTime.now())
                )
        );

        webTestClient.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserResponse.class)
                .hasSize(3);
    }

    @Test
    void findUser() {
        when(userService.findById(1L)).thenReturn(
                Mono.just(new User(1L, "greg", "greg@afsdkfj.com", LocalDateTime.now(), LocalDateTime.now()))
        );

        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals("greg", res.getName());
                    assertEquals("greg@afsdkfj.com", res.getEmail());
                });
    }

    @Test
    void deleteUser() {
        when(userService.deleteById(1L)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/users/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void updateUser() {
        when(userService.update(1L, "greg_updated", "new_email@mail.com")).thenReturn(
                Mono.just(new User(1L, "greg_updated", "new_email@mail.com", LocalDateTime.now(), LocalDateTime.now()))
        );

        webTestClient.put().uri("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateRequest("greg_updated", "new_email@mail.com"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals("greg_updated", res.getName());
                    assertEquals("new_email@mail.com", res.getEmail());
                });
    }
}
