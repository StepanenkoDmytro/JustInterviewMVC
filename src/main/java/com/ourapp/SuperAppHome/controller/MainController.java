package com.ourapp.SuperAppHome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
public class MainController {

    @GetMapping("/main")
    public String main(){
        return "main";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

}
