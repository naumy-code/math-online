package com.wyw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.wyw")
@ComponentScan(basePackages = {"com.wyw"})
public class ExamsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamsystemApplication.class, args);
    }

}

