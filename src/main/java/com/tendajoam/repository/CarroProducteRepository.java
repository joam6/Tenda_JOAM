package com.tendajoam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tendajoam.entity.cart.CarroProducte;
import com.tendajoam.entity.cart.CarroProducteId;

public interface CarroProducteRepository extends JpaRepository<CarroProducte, CarroProducteId> {
}
