package com.tendajoam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tendajoam.entity.product.Producte;

public interface ProducteRepository extends JpaRepository<Producte, String> {
}
