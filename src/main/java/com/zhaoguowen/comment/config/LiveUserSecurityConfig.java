package com.zhaoguowen.comment.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhaoguowen.comment.authentication.admin.AdminAuthenticationFailureHandler;
import com.zhaoguowen.comment.authentication.admin.AdminAuthenticationSuccessHandler;
import com.zhaoguowen.comment.authentication.admin.AdminLogoutSuccessHandler;
import com.zhaoguowen.comment.authentication.admin.AdminUserDetailService;
import com.zhaoguowen.comment.authentication.liveUser.*;
import com.zhaoguowen.comment.mapper.LiveUserMapper;
import com.zhaoguowen.comment.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.Filter;


/**
 * 直播前台安全环境配置
 */
@Configuration
@Slf4j
public class LiveUserSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private LiveUserMapper liveUserMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(liveUserAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).
                formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/liveLogin").
                and().
                authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/login", "/liveLogin", "/logout").permitAll()
                .antMatchers("/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable()
                .logout()
                .logoutUrl("/logout").deleteCookies("JSESSIONID").logoutSuccessHandler(liveUserLogoutSuccessHandler());


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(liveUserAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.debug(false);
    }

    @Bean
    public Filter liveUserAuthenticationFilter() throws Exception {
        LiveUserAuthenticationFilter filter = new LiveUserAuthenticationFilter();
        //设置authenticationManager
        filter.setAuthenticationManager(this.authenticationManager());
        filter.setAuthenticationFailureHandler(liveUserAuthenticationFailureHandler());
        filter.setAuthenticationSuccessHandler(liveUserAuthenticationSuccessHandler());
        return filter;
    }


    @Bean
    public AuthenticationProvider liveUserAuthenticationProvider() {
        LiveUserAuthenticationProvider provider = new LiveUserAuthenticationProvider(liveUserDetailsService());
        return provider;
    }


    @Bean
    public UserDetailsService liveUserDetailsService() {
        LiveUserDetailService userDetailsService = new LiveUserDetailService(liveUserMapper);
        return userDetailsService;
    }


    @Bean
    public AuthenticationFailureHandler liveUserAuthenticationFailureHandler() {
        return new LiveUserAuthenticationFailureHandler(objectMapper);
    }

    @Bean
    public AuthenticationSuccessHandler liveUserAuthenticationSuccessHandler() {
        return new LiveUserAuthenticationSuccessHandler();
    }

    @Bean
    public LogoutSuccessHandler liveUserLogoutSuccessHandler() {
        return new LiveUserLogoutSuccessHandler("/");
    }
}
