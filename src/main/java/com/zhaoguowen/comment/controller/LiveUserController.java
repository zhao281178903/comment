package com.zhaoguowen.comment.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class LiveUserController {


    @GetMapping
    public String index() {

        return "live-user/index";
    }


    @GetMapping("/me")
    @ResponseBody
    public Object me(@AuthenticationPrincipal Authentication authentication) {
        return authentication.getPrincipal();
    }
}
