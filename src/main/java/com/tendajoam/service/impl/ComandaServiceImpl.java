package com.tendajoam.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tendajoam.entity.cart.Carro;
import com.tendajoam.entity.cart.CarroProducte;
import com.tendajoam.entity.order.Comanda;
import com.tendajoam.entity.users.Cliente;
import com.tendajoam.repository.CarroProducteRepository;
import com.tendajoam.repository.CarroRepository;
import com.tendajoam.repository.ClienteRepository;
import com.tendajoam.repository.ComandaRepository;
import com.tendajoam.service.interfaces.ComandaService;

import jakarta.transaction.Transactional;

@Service
public class ComandaServiceImpl implements ComandaService {

	private final ComandaRepository comandaRepository;
	private final CarroRepository carroRepo;
	private final CarroProducteRepository carroProducteRepo;
	private final ClienteRepository clienteRepo;

	public ComandaServiceImpl(ComandaRepository comandaRepository, CarroRepository carroRepo,
			CarroProducteRepository carroProducteRepo, ClienteRepository clienteRepo) {
		this.comandaRepository = comandaRepository;
		this.carroRepo = carroRepo;
		this.carroProducteRepo = carroProducteRepo;
		this.clienteRepo = clienteRepo;
	}

	@Override
	public List<Comanda> findAll() {
		return comandaRepository.findAll();
	}

	@Override
	public Optional<Comanda> findById(String id) {
		return comandaRepository.findById(id);
	}

	@Override
	public Comanda save(Comanda comanda) {
		return comandaRepository.save(comanda);
	}

	@Override
	public void delete(String id) {
		comandaRepository.deleteById(id);
	}

	// ... dins de ComandaServiceImpl.java ...

	@Override
	public String findLastId() {
	    return comandaRepository.findLastId();
	}

	@Override
	public List<Comanda> findByCliente(String idCliente) {
	    // Aquí cridem el nou mètode del repositori que hem creat abans
	    return comandaRepository.findByCliente_IdUsuari(idCliente);
	}

	@Override
	@Transactional
	public Comanda comprar(String idCliente) {
	    // 1. Obtenir carro
	    Carro carro = carroRepo.findById(idCliente + "_carro")
	            .orElseThrow(() -> new RuntimeException("Carro no trobat"));

	    // 2. Comprovar si està buit (utilitzant la col·lecció del carro)
	    if (carro.getCarroProductes() == null || carro.getCarroProductes().isEmpty()) {
	        throw new RuntimeException("El carro està buit");
	    }

	    // 3. Calcular total
	    double total = carro.getCarroProductes().stream()
	            .mapToDouble(cp -> cp.getProducte().getPreu() * cp.getQuantitat()).sum();
		
	    // 4. Crear comanda amb ID personalitzat
		Comanda comanda = new Comanda();

		// Generació de l'ID "comanXXXX"
		String ultimId = findLastId();
		int nouNumero = 1;
		if (ultimId != null && ultimId.startsWith("coman")) {
			String numeroPart = ultimId.replace("coman", "");
			nouNumero = Integer.parseInt(numeroPart) + 1;
		}
		String nouId = String.format("coman%04d", nouNumero);

		comanda.setIdComanda(nouId); // S'assigna l'ID calculat
		comanda.setCliente(carro.getCliente());
		comanda.setData(new Date(System.currentTimeMillis()));
		comanda.setEstat("PAGADA");
		comanda.setTotal(total);

		comandaRepository.save(comanda);

		carro.getCarroProductes().clear(); 
	    carro.setTotal(0);
	    
	    // Aquest save ara no provocarà l'error perquè la col·lecció és buida.
	    carroRepo.save(carro);

	    // 6. Actualitzar punts
	    Cliente cli = carro.getCliente();
	    cli.setPuntos(cli.getPuntos() + (int) total / 10);
	    clienteRepo.save(cli);
	    
		return comanda;
	}
	


}
