package com.zhaoguowen.comment.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
//@RequestMapping("/admin")
public class AuthenticationController {


//    @GetMapping("/authentication/require")
    public String authenticationForm() {
        return "admin/login";
    }
}
