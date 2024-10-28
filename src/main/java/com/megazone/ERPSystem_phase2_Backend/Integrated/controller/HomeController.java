package com.megazone.ERPSystem_phase2_Backend.Integrated.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String greeting() {
        return "index";
    }

}