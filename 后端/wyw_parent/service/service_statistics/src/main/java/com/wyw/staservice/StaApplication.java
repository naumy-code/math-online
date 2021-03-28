package com.wyw.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Administrator
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.wyw"})
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.wyw.staservice.mapper")
@EnableScheduling   // 定时任务注解
public class StaApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class, args);
    }
}
