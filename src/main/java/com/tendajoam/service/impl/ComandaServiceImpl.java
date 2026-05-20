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

    public ComandaServiceImpl(
            ComandaRepository comandaRepository,
            CarroRepository carroRepo,
            CarroProducteRepository carroProducteRepo,
            ClienteRepository clienteRepo
    ) {
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

    @Override
    @Transactional
    public Comanda comprar(String idCliente) {

        // 1. Obtenir el carro del client
        Carro carro = carroRepo.findById(idCliente + "_carro")
                .orElseThrow(() -> new RuntimeException("Carro no trobat"));

        // 2. Obtenir productes del carro
        List<CarroProducte> items = carroProducteRepo.findAll().stream()
                .filter(cp -> cp.getCarro().getIdCarrito().equals(carro.getIdCarrito()))
                .toList();

        if (items.isEmpty()) {
            throw new RuntimeException("El carro està buit");
        }

        // 3. Calcular total
        double total = items.stream()
                .mapToDouble(cp -> cp.getProducte().getPreu() * cp.getQuantitat())
                .sum();

        // 4. Crear comanda
        Comanda comanda = new Comanda();
        comanda.setIdComanda("CMD_" + System.currentTimeMillis());
        comanda.setCliente(carro.getCliente());
        comanda.setData(new Date(System.currentTimeMillis()));
        comanda.setEstat("PAGADA");
        comanda.setTotal(total);

        comandaRepository.save(comanda);

        // 5. Buidar carro
        carroProducteRepo.deleteAll(items);
        carro.setTotal(0);
        carroRepo.save(carro);

        // 6. Actualitzar punts del client
        Cliente cli = carro.getCliente();
        cli.setPuntos(cli.getPuntos() + (int) total / 10);
        clienteRepo.save(cli);

        return comanda;
    }
}
