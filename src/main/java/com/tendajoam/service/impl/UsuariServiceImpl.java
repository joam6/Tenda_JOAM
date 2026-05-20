package com.tendajoam.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tendajoam.entity.users.Usuari;
import com.tendajoam.repository.UsuariRepository;
import com.tendajoam.service.interfaces.UsuariService;

@Service
public class UsuariServiceImpl implements UsuariService {

    private final UsuariRepository usuariRepository;

    public UsuariServiceImpl(UsuariRepository usuariRepository) {
        this.usuariRepository = usuariRepository;
    }

    @Override
    public List<Usuari> findAll() {
        return usuariRepository.findAll();
    }

    @Override
    public Optional<Usuari> findById(String id) {
        return usuariRepository.findById(id);
    }

    @Override
    public Usuari save(Usuari usuari) {
        return usuariRepository.save(usuari);
    }

    @Override
    public void delete(String id) {
        usuariRepository.deleteById(id);
    }

    @Override
    public Usuari findByEmail(String email) {
        return usuariRepository.findByEmail(email);
    }
}
