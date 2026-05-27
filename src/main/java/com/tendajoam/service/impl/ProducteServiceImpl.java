package com.tendajoam.service.impl;

import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;

import com.tendajoam.entity.product.Producte;
import com.tendajoam.entity.users.Venedor;
import com.tendajoam.repository.ProducteRepository;
import com.tendajoam.repository.VenedorRepository;
import com.tendajoam.service.interfaces.ProducteService;

@Service
public class ProducteServiceImpl implements ProducteService {

	private final ProducteRepository producteRepository;
	private final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/img/";
	private final VenedorRepository venedorRepository;

	public ProducteServiceImpl(ProducteRepository producteRepository, VenedorRepository venedorRepository) {
		this.producteRepository = producteRepository;
		this.venedorRepository = venedorRepository;
	}

	@Override
	public List<Producte> findAll() {
		return producteRepository.findAll();
	}

	@Override
	public Optional<Producte> findById(String id) {
		return producteRepository.findById(id);
	}

	@Override
	public Producte save(Producte producte) {
		return producteRepository.save(producte);
	}

	@Override
	public void delete(String id) {
		producteRepository.deleteById(id);
	}

	@Override
	public void guardarProducteAmbImatge(String nom, double preu, String categoria, String descripcio, int stock,
			MultipartFile imatge, String idVenedor) { // 1. NETEJA DE SEGURETAT: Si el client envia "ven03,ven03",
														// agafem només la
		// primera part
		String idNetejat = (idVenedor != null && idVenedor.contains(",")) ? idVenedor.split(",")[0] : idVenedor;

		System.out.println("DEBUG: ID rebut: " + idVenedor + " -> ID utilitzat: " + idNetejat);

		try {
			// 1. Gestió de la imatge
			String nomFitxer = (imatge != null && !imatge.isEmpty())
					? UUID.randomUUID().toString() + "_" + imatge.getOriginalFilename()
					: "default.png";

			if (imatge != null && !imatge.isEmpty()) {
				Files.copy(imatge.getInputStream(), Paths.get(UPLOAD_DIR + nomFitxer));
			}

			// 2. BUSCANT EL VENEDOR AMB L'ID NETEJAT
			Venedor venedor = venedorRepository.findById(idNetejat)
					.orElseThrow(() -> new RuntimeException("Venedor no trobat amb ID: " + idNetejat));

			// 3. Crear i guardar
			Producte p = new Producte();
			p.setIdProducte(UUID.randomUUID().toString());
			p.setNom(nom);
			p.setPreu(preu);
			p.setCategoria(categoria);
			p.setDescripcio(descripcio);
			p.setStock(stock);
			p.setImatge(nomFitxer);
			p.setVenedor(venedor);

			producteRepository.save(p);
		} catch (IOException e) {
			throw new RuntimeException("Error al processar el fitxer: " + e.getMessage());
		}
	}
}
