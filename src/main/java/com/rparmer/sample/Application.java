package com.rparmer.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.rparmer.sample" } )
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
