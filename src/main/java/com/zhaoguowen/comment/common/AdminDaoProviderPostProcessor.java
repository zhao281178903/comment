package com.zhaoguowen.comment.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Component;


@Component
public class AdminDaoProviderPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAssignableFrom(DaoAuthenticationProvider.class)) {
            DaoAuthenticationProvider daoAuthenticationProvider = (DaoAuthenticationProvider) bean;
            daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        }
        return bean;
    }
}
