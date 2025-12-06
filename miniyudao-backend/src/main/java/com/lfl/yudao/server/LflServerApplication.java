package com.lfl.yudao.server;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lfl.yudao.server.mapper")
public class LflServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LflServerApplication.class, args);
    }
}
