package com.demo.webflux.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author ZhengYingjie
 * @Date 2019-07-30
 * @Description
 */
@Controller
public class FileController {

    @RequestMapping("/lottery")
    public ResponseEntity<Resource> index(ServerHttpRequest request, ServerHttpResponse response) {

        //文件头
        List<String> strings = Arrays.asList("序号", "时间", "地点", "名称");
        // 文件体
        List<Entity> entities = Arrays.asList(new Entity(1, "201910909", "朝阳", "黑桃"), new Entity(1, "20190202", "海淀", "红桃"));

        StringBuffer buffer = new StringBuffer();
        strings.forEach(str -> {
            buffer.append(str).append(",");
        });
        buffer.append("\r\n");
        entities.forEach(entity -> {
            buffer.append(entity.getNum()).append(",").append(entity.getTime()).append(",").append(entity.getAddress()).append(",").append(entity.getName()).append("\r\n");
        });
        byte[] gbks = new byte[1024];
        try {
            gbks = buffer.toString().getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=demo.csv")
                .header("Accept-Ranges", "bytes")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .body(new ByteArrayResource(gbks));

    }
    @Data
    @AllArgsConstructor
    private class Entity {
        private Integer num;

        private String time;

        private String address;

        private String name;
    }
}
