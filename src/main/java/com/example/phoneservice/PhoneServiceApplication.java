package com.example.phoneservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class PhoneServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhoneServiceApplication.class, args);
    }

    @PostConstruct
    public void init() {
        System.out.println(" ****************************************** ");
        System.out.println(" *  http://localhost:8080                 * ");
        System.out.println(" *  http://localhost:8080/swagger-ui.html * ");
        System.out.println(" ******************************************");
    }

}
