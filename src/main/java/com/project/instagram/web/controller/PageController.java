package com.project.instagram.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("")
    public String loadMainPage() {
        return "main";
    }

    @GetMapping("/sign-in")
    public String loadLoginPage() {
        return "/auth/login";
    }

    @GetMapping("/sign-up")
    public String loadSignUpPage() {
        return "/auth/sign-up";
    }
}