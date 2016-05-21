package com.zgardner.springBootIntro.controller;

import org.springframework.web.bind.annotation.RestController;

import com.zgardner.springBootIntro.model.PersonModel;
import com.zgardner.springBootIntro.service.*;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class SpringBootIntroController {

	@RequestMapping("/")
    public String index() {
        return "Test";
    }
}
