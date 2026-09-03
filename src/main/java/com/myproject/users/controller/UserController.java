package com.myproject.users.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String test() {
        return "Heloo From UserController";
    }

    @GetMapping("/second")
    public String test1() {
        return "Second Hello";
    }

}
