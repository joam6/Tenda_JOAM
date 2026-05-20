package com.tendajoam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tendajoam.entity.users.Usuari;

public interface UsuariRepository extends JpaRepository<Usuari, String> {
    Usuari findByEmail(String email);
}
