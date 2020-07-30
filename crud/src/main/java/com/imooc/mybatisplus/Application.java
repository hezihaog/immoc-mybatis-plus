package com.imooc.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hezihao
 * @version 1.0
 * <p>
 * @date 2020/7/29 8:39 下午
 * <p>
 */
@SpringBootApplication
//指定MyBatis扫描的包路径
@MapperScan("com.imooc.mybatisplus.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}