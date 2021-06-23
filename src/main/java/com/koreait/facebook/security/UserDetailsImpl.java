package com.koreait.facebook.security;

import com.koreait.facebook.user.model.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private UserEntity user;

    public UserDetailsImpl(UserEntity user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPw();
    }

    @Override
    public String getUsername() {
        return user.getNm();
    }

    @Override
    public boolean isAccountNonExpired() { //계정 만료됨?
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //계정 잠금?
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() { //계정 활성화?
        return true;
    }
}