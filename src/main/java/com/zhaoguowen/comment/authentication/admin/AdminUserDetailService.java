package com.zhaoguowen.comment.authentication.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhaoguowen.comment.entity.User;
import com.zhaoguowen.comment.exception.AdminUsernameNotFoundException;
import com.zhaoguowen.comment.mapper.UserMapper;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public class AdminUserDetailService implements UserDetailsService {


    private UserMapper userMapper;


    public AdminUserDetailService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = new User();
        user.setName(username);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        return userInfo;
    }

    @ToString
    private static class UserInfo extends User implements UserDetails {


        @Override
        public boolean equals(Object rhs) {
            if (rhs instanceof UserInfo) {
                return getUsername().equals(((UserInfo) rhs).getUsername());
            }
            return false;
        }

        /**
         * Returns the hashcode of the {@code username}.
         */
        @Override
        public int hashCode() {
            return getUsername().hashCode();
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return AuthorityUtils.createAuthorityList("ROLE_ADMIN");
        }

        @Override
        @JsonIgnore
        public String getPassword() {
            return super.getPassword();
        }

        @Override
        public String getUsername() {
            return super.getName();
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
