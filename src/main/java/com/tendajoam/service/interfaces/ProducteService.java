package com.tendajoam.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.tendajoam.entity.product.Producte;

public interface ProducteService {

	List<Producte> findAll();

	Optional<Producte> findById(String id);

	Producte save(Producte producte);

	void delete(String id);

	void guardarProducteAmbImatge(String nom, double preu, String categoria, String descripcio, int stock,
			MultipartFile imatge, String idVenedor);
}
