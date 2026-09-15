package com.example.projectTLearn.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectTLearn.model.UserModel;

@RestController
public class HelloWord {
    @RequestMapping("/hello")
    public String hello(@AuthenticationPrincipal UserModel user) {
        return "Hello World + " + user.getName();
    }
}
