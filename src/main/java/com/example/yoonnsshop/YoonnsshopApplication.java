package com.example.yoonnsshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class YoonnsshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoonnsshopApplication.class, args);
    }

}
