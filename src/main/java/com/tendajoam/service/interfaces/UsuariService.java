package com.tendajoam.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.tendajoam.entity.users.Usuari;

public interface UsuariService {

    List<Usuari> findAll();
    Optional<Usuari> findById(String id);
    Usuari save(Usuari usuari);
    void delete(String id);
    Usuari findByEmail(String email);
}
