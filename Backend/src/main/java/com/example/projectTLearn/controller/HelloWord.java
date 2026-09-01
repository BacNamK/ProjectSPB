package com.example.projectTLearn.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWord {
    @RequestMapping("/hello")
    public String hello() {

        Object principal = SecurityContextHolder.getContext().getAuthentication();

        return "Hello World + " + principal.toString();
    }
}
