package com.tendajoam.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tendajoam.entity.product.Producte;
import com.tendajoam.repository.ProducteRepository;
import com.tendajoam.service.interfaces.ProducteService;

@Service
public class ProducteServiceImpl implements ProducteService {

    private final ProducteRepository producteRepository;

    public ProducteServiceImpl(ProducteRepository producteRepository) {
        this.producteRepository = producteRepository;
    }

    @Override
    public List<Producte> findAll() {
        return producteRepository.findAll();
    }

    @Override
    public Optional<Producte> findById(String id) {
        return producteRepository.findById(id);
    }

    @Override
    public Producte save(Producte producte) {
        return producteRepository.save(producte);
    }

    @Override
    public void delete(String id) {
        producteRepository.deleteById(id);
    }
}
