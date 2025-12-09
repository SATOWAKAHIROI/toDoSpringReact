package com.example.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/users")
public class UserController{
    @GetMapping("/login")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
}