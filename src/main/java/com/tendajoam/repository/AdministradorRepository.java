package com.tendajoam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tendajoam.entity.users.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, String> {
}
