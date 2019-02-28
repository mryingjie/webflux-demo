package com.demo.webflux.repository;

import com.demo.webflux.bean.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * @author ZhengYingjie
 * @time 2019/2/28 11:37
 * @description
 */
public interface UserRepository extends ReactiveCrudRepository<User, String> {

    Mono<User> findByUsername(String username);

    Mono<Long> deleteByUsername(String username);
}
