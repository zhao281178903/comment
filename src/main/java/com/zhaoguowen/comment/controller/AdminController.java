package com.zhaoguowen.comment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {


//    @Autowired
//    private SessionRegistry sessionRegistry;

    @GetMapping
    public String index() {

        return "admin/index";
    }


    @GetMapping("/me")
    @ResponseBody
    public Object me(@AuthenticationPrincipal Authentication authentication) {
        return authentication.getPrincipal();
    }


//    @GetMapping("/users")
//    @ResponseBody
//    public Object users() {
//        return sessionRegistry.getAllPrincipals();
//    }
}
