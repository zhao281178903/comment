package com.zhaoguowen.comment.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhaoguowen.comment.authentication.admin.AdminAuthenticationFailureHandler;
import com.zhaoguowen.comment.authentication.admin.AdminAuthenticationSuccessHandler;
import com.zhaoguowen.comment.authentication.admin.AdminLogoutSuccessHandler;
import com.zhaoguowen.comment.authentication.admin.AdminUserDetailService;
import com.zhaoguowen.comment.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;


/**
 * 后台安全环境配置
 */
@Configuration
@Slf4j
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/admin/**")
                .authorizeRequests()
                .antMatchers("/admin/authentication/form", "/admin/authentication/require", "/admin/logout").permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/admin/authentication/require")
                .loginProcessingUrl("/admin/authentication/form")
                .failureHandler(adminAuthenticationFailureHandler())
                .successHandler(adminAuthenticationSuccessHandle())
                .and()
                .csrf().
                disable()
                .sessionManagement()
                .maximumSessions(1)
//                sessionRegistry(sessionRegistry()).


                .and().and()
                .logout().logoutUrl("/admin/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(adminLogoutSuccessHandler());


    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminUserDetailService()).passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService adminUserDetailService() {
        AdminUserDetailService userDetailsService = new AdminUserDetailService(userMapper);
        return userDetailsService;
    }


    @Bean
    public AuthenticationFailureHandler adminAuthenticationFailureHandler() {
        return new AdminAuthenticationFailureHandler(objectMapper);
    }

    @Bean
    public AuthenticationSuccessHandler adminAuthenticationSuccessHandle() {
        return new AdminAuthenticationSuccessHandler();
    }


    @Bean
    public LogoutSuccessHandler adminLogoutSuccessHandler() {
        return new AdminLogoutSuccessHandler("/admin");
    }


    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new HttpSessionEventPublisher());
        return servletListenerRegistrationBean;
    }

//    @Bean
//    public SessionRegistry sessionRegistry() {
//        return new SessionRegistryImpl();
//    }

}
