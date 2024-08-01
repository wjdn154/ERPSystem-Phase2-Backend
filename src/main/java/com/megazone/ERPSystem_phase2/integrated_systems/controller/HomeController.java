package com.megazone.ERPSystem_phase2.integrated_systems.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @GetMapping("/")
    public String greeting() {
        return "index";
    }

}