package com.tendajoam.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.tendajoam.entity.cart.Carro;

public interface CarroService {

	// CRUD bàsic
	List<Carro> findAll();
	Optional<Carro> findById(String id);
	Carro save(Carro carro);
	void delete(String id);

	// Lògica del carro
	Carro getCarroByCliente(String idCliente);

	void afegirProducte(String idCliente, String idProducte, int quantitat);

	void eliminarProducte(String idCliente, String idProducte);

	void buidarCarro(String idCliente);
}
