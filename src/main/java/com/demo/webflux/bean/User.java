package com.demo.webflux.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author ZhengYingjie
 * @time 2019/2/28 10:06
 * @description
 */
@Data
@AllArgsConstructor
@Document
public class User {

    @Id
    private String id;      // 注解属性id为ID
    @Indexed(unique = true) // 注解属性username为索引，并且不能重复
    private String username;
    private String name;
    private String phone;
    private Date birthday;
}
