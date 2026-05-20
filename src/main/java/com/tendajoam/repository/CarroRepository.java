package com.tendajoam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tendajoam.entity.cart.Carro;

public interface CarroRepository extends JpaRepository<Carro, String> {
}
