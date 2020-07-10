package org.liunian.frontend.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients(basePackages = {"org.liunian.frontend.client"})
@SpringBootApplication
public class LiunianApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiunianApplication.class, args);
    }

}
