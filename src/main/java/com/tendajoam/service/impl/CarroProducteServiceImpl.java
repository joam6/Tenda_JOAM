package com.tendajoam.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tendajoam.entity.cart.CarroProducte;
import com.tendajoam.entity.cart.CarroProducteId;
import com.tendajoam.repository.CarroProducteRepository;
import com.tendajoam.service.interfaces.CarroProducteService;

@Service
public class CarroProducteServiceImpl implements CarroProducteService {

    private final CarroProducteRepository carroProducteRepository;

    public CarroProducteServiceImpl(CarroProducteRepository carroProducteRepository) {
        this.carroProducteRepository = carroProducteRepository;
    }

    @Override
    public List<CarroProducte> findAll() {
        return carroProducteRepository.findAll();
    }

    @Override
    public Optional<CarroProducte> findById(CarroProducteId id) {
        return carroProducteRepository.findById(id);
    }

    @Override
    public CarroProducte save(CarroProducte carroProducte) {
        return carroProducteRepository.save(carroProducte);
    }

    @Override
    public void delete(CarroProducteId id) {
        carroProducteRepository.deleteById(id);
    }
}
