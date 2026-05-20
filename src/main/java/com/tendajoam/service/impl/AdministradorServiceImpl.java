package com.tendajoam.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tendajoam.entity.users.Administrador;
import com.tendajoam.repository.AdministradorRepository;
import com.tendajoam.service.interfaces.AdministradorService;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    private final AdministradorRepository administradorRepository;

    public AdministradorServiceImpl(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

    @Override
    public List<Administrador> findAll() {
        return administradorRepository.findAll();
    }

    @Override
    public Optional<Administrador> findById(String id) {
        return administradorRepository.findById(id);
    }

    @Override
    public Administrador save(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    @Override
    public void delete(String id) {
        administradorRepository.deleteById(id);
    }
}
