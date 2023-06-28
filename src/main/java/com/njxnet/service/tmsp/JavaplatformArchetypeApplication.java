package com.njxnet.service.tmsp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.njxnet"})
@MapperScan(basePackages = {"com.njxnet.service.tmsp.dao"})
public class JavaplatformArchetypeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaplatformArchetypeApplication.class, args);
    }

}
