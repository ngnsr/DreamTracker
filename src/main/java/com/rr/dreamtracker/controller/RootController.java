package com.rr.dreamtracker.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public String publicPage(){
        return "hello";
    }

    @GetMapping("/secure")
    public String get(Authentication authentication){
        if(authentication != null)
            return "hello " + authentication.getName();

        return "hello";
    }
}
