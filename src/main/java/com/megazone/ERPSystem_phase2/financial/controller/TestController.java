package com.megazone.ERPSystem_phase2.financial.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String greeting() {
        return "hello";
    }

}