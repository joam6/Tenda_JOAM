package com.tendajoam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tendajoam.entity.users.Usuari;
import com.tendajoam.service.interfaces.UsuariService;

@RestController
public class DebugController {

    private final UsuariService usuariService;

    public DebugController(UsuariService usuariService) {
        this.usuariService = usuariService;
    }

    @GetMapping("/debug/users")
    public List<Usuari> getAllUsers() {
        return usuariService.findAll();
    }
}
