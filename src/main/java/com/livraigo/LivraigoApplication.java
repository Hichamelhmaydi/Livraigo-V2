package com.livraigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.livraigo")
public class LivraigoApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(LivraigoApplication.class, args);
    }
}