package com.tendajoam.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.tendajoam.entity.order.Comanda;

public interface ComandaService {

	List<Comanda> findAll();

	Optional<Comanda> findById(String id);

	Comanda save(Comanda comanda);

	void delete(String id);

	Comanda comprar(String idCliente);
}
