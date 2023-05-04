package com.mi.mall1227;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mi.mall1227.mapper")
public class Mall1227Application {

    public static void main(String[] args) {
        SpringApplication.run(Mall1227Application.class, args);
    }

}
