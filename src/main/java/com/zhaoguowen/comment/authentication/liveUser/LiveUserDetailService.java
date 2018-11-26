package com.zhaoguowen.comment.authentication.liveUser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhaoguowen.comment.entity.LiveUser;
import com.zhaoguowen.comment.entity.User;
import com.zhaoguowen.comment.mapper.LiveUserMapper;
import com.zhaoguowen.comment.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public class LiveUserDetailService implements UserDetailsService {


    private LiveUserMapper liveUserMapper;


    public LiveUserDetailService(LiveUserMapper liveUserMapper) {
        this.liveUserMapper = liveUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LiveUser user = new LiveUser();
        user.setUsername(username);
        QueryWrapper<LiveUser> queryWrapper = new QueryWrapper<>(user);
        user = liveUserMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        LiveUserInfo userInfo = new LiveUserInfo();
        BeanUtils.copyProperties(user, userInfo);
        return userInfo;
    }

    private static class LiveUserInfo extends LiveUser implements UserDetails {

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return AuthorityUtils.createAuthorityList("ROLE_USER");
        }

        @Override
        @JsonIgnore
        public String getPassword() {
            return null;
        }

        @Override
        @JsonIgnore
        public String getUsername() {
            return super.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
