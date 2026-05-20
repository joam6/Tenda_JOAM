package com.tendajoam.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tendajoam.entity.users.Usuari;

public class CustomUserDetails implements UserDetails {

    private final Usuari usuari;

    public CustomUserDetails(Usuari usuari) {
        this.usuari = usuari;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuari.getRol()));
    }

    @Override
    public String getPassword() {
        return usuari.getPass();
    }

    @Override
    public String getUsername() {
        return usuari.getEmail();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
