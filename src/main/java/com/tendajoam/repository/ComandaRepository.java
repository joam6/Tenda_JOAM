package com.tendajoam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tendajoam.entity.order.Comanda;

public interface ComandaRepository extends JpaRepository<Comanda, String> {
    
    @Query("SELECT c.idComanda FROM Comanda c ORDER BY c.idComanda DESC LIMIT 1")
    String findLastId();

    List<Comanda> findByCliente_IdUsuari(String idUsuari);
}
