package com.zhaoguowen.comment.authentication.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhaoguowen.comment.common.JsonData;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 后台管理登录失败处理器
 */
public class AdminAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper;


    public AdminAuthenticationFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message = "";

        if (exception instanceof BadCredentialsException) {
            message = "密码错误";
        } else if (exception instanceof LockedException) {
            message = "账号被锁定";
        } else {
            message = exception.getMessage();
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        JsonData jsonData = JsonData.fail(message);
        response.getWriter().write(objectMapper.writeValueAsString(jsonData));
    }
}
