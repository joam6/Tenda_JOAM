package com.tendajoam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tendajoam.entity.users.Venedor;

public interface VenedorRepository extends JpaRepository<Venedor, String> {
}
