package com.zhaoguowen.comment.authentication.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class AdminLogoutSuccessHandler implements LogoutSuccessHandler {

    private String targetUrl;


    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public AdminLogoutSuccessHandler(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        UserDetails user = (UserDetails) authentication.getPrincipal();

        //todo 跳转前的相关操作
        log.info("后台用户：{},退出系统", user.getUsername());
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }


    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
