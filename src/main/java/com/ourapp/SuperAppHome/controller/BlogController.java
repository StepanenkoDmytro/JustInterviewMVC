package com.ourapp.SuperAppHome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
public class BlogController {

    @GetMapping("/blog")
    public String main(){
        return "blog-main";
    }
}
