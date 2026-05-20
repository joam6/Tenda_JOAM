package com.tendajoam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tendajoam.entity.users.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
}
