package com.tendajoam.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tendajoam.entity.users.Usuari;
import com.tendajoam.service.interfaces.UsuariService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuariService usuariService;

    public CustomUserDetailsService(UsuariService usuariService) {
        this.usuariService = usuariService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuari usuari = usuariService.findByEmail(email);
        if (usuari == null) {
            throw new UsernameNotFoundException("Usuari no trobat");
        }
        return new CustomUserDetails(usuari);
    }
}
