package com.example.health.healthtestproject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestEndpoint {
    @GetMapping("/test")
    public String test(){
        return "test";
    }
    @GetMapping("/testing")
    public String testing(){
        return "Testing";
    }
    @GetMapping("/test1")
    public String test1(){
        return "test1";
    }
    @PostMapping("/test2")
    public String test2(){
        return "test2";
    }
}
