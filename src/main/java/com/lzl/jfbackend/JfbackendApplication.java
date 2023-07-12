package com.lzl.jfbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lzl.jfbackend.mapper")
public class JfbackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(JfbackendApplication.class, args);
    }

}
