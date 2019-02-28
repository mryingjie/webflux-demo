package com.demo.webflux.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

/**
 * @author ZhengYingjie
 * @time 2019/2/27 19:01
 * @description
 */
@RestController
public class HelloController {


    @GetMapping("/hello")
    public Mono<String> hello(@RequestParam(required = false) ServerRequest serverRequest) {
        System.out.println(serverRequest);
        return Mono.just("welcome to spring-webflux");
    }

    /**
     * 返回包含时间字符串的ServerResponse
      */
    public HandlerFunction<ServerResponse> timeFunction =
            request -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(
                    Mono.just("Now is " + new SimpleDateFormat("HH:mm:ss").format(new Date())), String.class);


    /**
     * // 返回包含日期字符串的ServerResponse
     */
    public HandlerFunction<ServerResponse> dateFunction =
            request -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(
                    Mono.just("Today is " + new SimpleDateFormat("yyyy-MM-dd").format(new Date())), String.class
            );

    /**
     * 相当于@RequestMapping，
     * 用来判断什么样的url映射到那个具体的HandlerFunction，
     * 输入为请求，输出为装在Mono里边的Handlerfunction：
     */
    public RouterFunction<ServerResponse> router =
            RouterFunctions.route(GET("/time"), timeFunction)
                    .andRoute(GET("/date"), dateFunction);
}
