package com.tendajoam.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.tendajoam.entity.users.Administrador;

public interface AdministradorService {

    List<Administrador> findAll();
    Optional<Administrador> findById(String id);
    Administrador save(Administrador administrador);
    void delete(String id);
}
