package com.tendajoam.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.tendajoam.entity.cart.CarroProducte;
import com.tendajoam.entity.cart.CarroProducteId;

public interface CarroProducteService {

    List<CarroProducte> findAll();
    Optional<CarroProducte> findById(CarroProducteId id);
    CarroProducte save(CarroProducte carroProducte);
    void delete(CarroProducteId id);
}
