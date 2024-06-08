package com.example.yoonnsshop.config;

import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RestController
@Import(ApiControllerConfiguration.class)
public @interface ApiController {
}
