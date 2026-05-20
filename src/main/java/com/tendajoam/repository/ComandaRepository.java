package com.tendajoam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tendajoam.entity.order.Comanda;

public interface ComandaRepository extends JpaRepository<Comanda, String> {
}
