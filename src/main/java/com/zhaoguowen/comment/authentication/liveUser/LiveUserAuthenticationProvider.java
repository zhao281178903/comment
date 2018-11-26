package com.zhaoguowen.comment.authentication.liveUser;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class LiveUserAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;


    public LiveUserAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername((String) authentication.getPrincipal());
        LiveUserAuthenticationToken liveAuthenticationToken = new LiveUserAuthenticationToken(loadedUser, loadedUser.getAuthorities());
        liveAuthenticationToken.setDetails(authentication.getDetails());
        return liveAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(LiveUserAuthenticationToken.class);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
