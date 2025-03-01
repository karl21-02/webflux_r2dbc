package com.kangwon.webflux1.repository;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private final UserRepository userRepository = new UserRepositoryImpl();

    @Test
    void save() {
        var user = User.builder().name("greg").email("greg@fastcampus.co.kr").build();
        StepVerifier.create(userRepository.save(user))
                .assertNext(u -> {
                    assertEquals(1L, u.getId());
                    assertEquals("greg", u.getName());
                    assertEquals("greg@fastcampus.co.kr", u.getEmail());
                });
    }

    @Test
    void findAll() {
        userRepository.save(User.builder().name("greg").email("greg@fastcampus.co.kr").build());
        userRepository.save(User.builder().name("greg2").email("greg@fastcampus.co.kr").build());
        userRepository.save(User.builder().name("greg3").email("greg@fastcampus.co.kr").build());

        StepVerifier.create(userRepository.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findById() {
        userRepository.save(User.builder().name("greg").email("greg@fastcampus.co.kr").build());
        userRepository.save(User.builder().name("greg2").email("greg@fastcampus.co.kr").build());
        StepVerifier.create(userRepository.findById(2L))
                .assertNext(u -> {
                    assertEquals(2L, u.getId());
                    assertEquals("greg2", u.getName());
                });
    }

    @Test
    void deleteById() {
        userRepository.save(User.builder().name("greg").email("greg@fastcampus.co.kr").build());
        userRepository.save(User.builder().name("greg2").email("greg@fastcampus.co.kr").build());
        StepVerifier.create(userRepository.deleteById(1L))
                .expectNextCount(1)
                .verifyComplete();
    }
}