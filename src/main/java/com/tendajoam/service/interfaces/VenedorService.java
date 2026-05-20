package com.tendajoam.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.tendajoam.entity.users.Venedor;

public interface VenedorService {

    List<Venedor> findAll();
    Optional<Venedor> findById(String id);
    Venedor save(Venedor venedor);
    void delete(String id);
}
