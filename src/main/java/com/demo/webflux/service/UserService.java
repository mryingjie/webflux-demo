package com.demo.webflux.service;

import com.demo.webflux.bean.User;
import com.demo.webflux.repository.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ZhengYingjie
 * @time 2019/2/28 11:42
 * @description
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    /**
     * 保存或更新。
     * 如果传入的user没有id属性，由于username是unique的，在重复的情况下有可能报错，
     * 这时找到以保存的user记录用传入的user更新它。
     */
    public Mono<User> save(User user) {
        Mono<User> byUsername = userRepository.findByUsername(user.getUsername());

        return userRepository.save(user)
                .onErrorResume(e ->     // 1
                        userRepository.findByUsername(user.getUsername())   // 2
                                .flatMap(originalUser -> {      // 4
                                    user.setId(originalUser.getId());
                                    return userRepository.save(user);   // 3
                                }));
    }

    public Mono<Long> deleteByUsername(String username) {
        return userRepository.deleteByUsername(username);
    }

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Flux<User> findAll() {
        val all = userRepository.findAll();
        Disposable subscribe = all.subscribe(System.out::println);
        return all;
    }

}

