package com.zhaoguowen.comment.exception;

import org.springframework.security.core.AuthenticationException;

public class AdminUsernameNotFoundException extends AuthenticationException {

    public AdminUsernameNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public AdminUsernameNotFoundException(String msg) {
        super(msg);
    }
}
