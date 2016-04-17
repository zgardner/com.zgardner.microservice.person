package com.zgardner.springBootIntro.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class SpringBootIntroController {

	@RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
