package com.tendajoam.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tendajoam.entity.users.Venedor;
import com.tendajoam.repository.VenedorRepository;
import com.tendajoam.service.interfaces.VenedorService;

@Service
public class VenedorServiceImpl implements VenedorService {

    private final VenedorRepository venedorRepository;

    public VenedorServiceImpl(VenedorRepository venedorRepository) {
        this.venedorRepository = venedorRepository;
    }

    @Override
    public List<Venedor> findAll() {
        return venedorRepository.findAll();
    }

    @Override
    public Optional<Venedor> findById(String id) {
        return venedorRepository.findById(id);
    }

    @Override
    public Venedor save(Venedor venedor) {
        return venedorRepository.save(venedor);
    }

    @Override
    public void delete(String id) {
        venedorRepository.deleteById(id);
    }
}
