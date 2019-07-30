package com.demo.webflux.controller;

import com.demo.webflux.bean.User;
import com.demo.webflux.service.UserService;
import lombok.val;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Function;

/**
 * @author ZhengYingjie
 * @time 2019/2/28 11:46
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("")
    public Mono<User> save(User user) {
        Mono<User> c = this.userService.save(user);
                c.log();
        return c;

    }

    @DeleteMapping("/{username}")
    public Mono<Long> deleteByUsername(@PathVariable("username") String username) {
        return this.userService.deleteByUsername(username);
    }

    @GetMapping("/{username}")
    public Mono<User> findByUsername(@PathVariable("username") String username) {
        return this.userService.findByUsername(username);
    }

    @GetMapping(value = "",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<User> findAll() {
        return this.userService.findAll().delayElements(Duration.ofSeconds(10));
    }

}
