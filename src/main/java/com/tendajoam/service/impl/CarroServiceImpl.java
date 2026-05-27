package com.tendajoam.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tendajoam.entity.cart.Carro;
import com.tendajoam.entity.cart.CarroProducte;
import com.tendajoam.entity.cart.CarroProducteId;
import com.tendajoam.entity.product.Producte;
import com.tendajoam.entity.users.Cliente;
import com.tendajoam.repository.CarroProducteRepository;
import com.tendajoam.repository.CarroRepository;
import com.tendajoam.repository.ClienteRepository;
import com.tendajoam.repository.ProducteRepository;
import com.tendajoam.service.interfaces.CarroService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class CarroServiceImpl implements CarroService {

    private final CarroRepository carroRepo;
    private final ClienteRepository clienteRepo;
    private final ProducteRepository producteRepo;
    private final CarroProducteRepository carroProducteRepo;

    public CarroServiceImpl(CarroRepository carroRepo, ClienteRepository clienteRepo,
                            ProducteRepository producteRepo, CarroProducteRepository carroProducteRepo) {
        this.carroRepo = carroRepo;
        this.clienteRepo = clienteRepo;
        this.producteRepo = producteRepo;
        this.carroProducteRepo = carroProducteRepo;
    }

    // -------------------------
    // CRUD BÀSIC
    // -------------------------
    
    @PersistenceContext 
    private EntityManager entityManager; 
    
    @Override
    public List<Carro> findAll() {
        return carroRepo.findAll();
    }

    @Override
    public Optional<Carro> findById(String id) {
        return carroRepo.findById(id);
    }

    @Override
    public Carro save(Carro carro) {
        return carroRepo.save(carro);
    }

    @Override
    public void delete(String id) {
        carroRepo.deleteById(id);
    }

    // -------------------------
    // LÒGICA DEL CARRO
    // -------------------------
    @Override
    public Carro getCarroByCliente(String idCliente) {
        return carroRepo.findById(idCliente + "_carro")
                .orElseGet(() -> crearCarro(idCliente));
    }

    private Carro crearCarro(String idCliente) {
        Cliente cliente = clienteRepo.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Client no trobat"));

        Carro carro = new Carro();
        carro.setIdCarrito(idCliente + "_carro");
        carro.setCliente(cliente);
        carro.setTotal(0);

        return carroRepo.save(carro);
    }

    @Override
    public void afegirProducte(String idCliente, String idProducte, int quantitat) {

        Carro carro = getCarroByCliente(idCliente);
        Producte producte = producteRepo.findById(idProducte)
                .orElseThrow(() -> new RuntimeException("Producte no trobat"));

        CarroProducteId id = new CarroProducteId(carro.getIdCarrito(), idProducte);

        Optional<CarroProducte> existent = carroProducteRepo.findById(id);

        if (existent.isPresent()) {
            CarroProducte cp = existent.get();
            cp.setQuantitat(cp.getQuantitat() + quantitat);
            carroProducteRepo.save(cp);
        } else {
            CarroProducte cp = new CarroProducte(carro, producte, quantitat);
            carroProducteRepo.save(cp);
        }

        recalcularTotal(carro);
    }

    @Override
    public void eliminarProducte(String idCliente, String idProducte) {
        Carro carro = getCarroByCliente(idCliente);
        String idCarrito = carro.getIdCarrito();

        // 1. Esborrat natiu (ja ho tens i funciona)
        carroProducteRepo.deleteByNativeQuery(idCarrito, idProducte);
        
        // 2. FORÇA la base de dades a descartar tot el que hi ha en memòria
        entityManager.flush();
        entityManager.clear();

        // 3. RECÀRREGA el carro des de la base de dades després d'esborrar
        Carro carroActualitzat = carroRepo.findById(idCarrito).orElse(carro);
        
        // 4. Recalcula amb l'objecte refrescat
        recalcularTotal(carroActualitzat);
    }

    private void recalcularTotal(Carro carro) {
        // Aquest mètode ara rebrà un carro net de la base de dades
        List<CarroProducte> items = carroProducteRepo.findByCarro_IdCarrito(carro.getIdCarrito());
        
        double total = items.stream()
                            .mapToDouble(cp -> cp.getProducte().getPreu() * cp.getQuantitat())
                            .sum();

        carro.setTotal(total);
        carroRepo.save(carro);
    }

    @Override
    public void buidarCarro(String idCliente) {
        Carro carro = getCarroByCliente(idCliente);

        // 1. Esborrem els registres de la taula (Query nativa com hem fet abans)
        // Assegura't de tenir aquest mètode al repository o fer-ho amb EntityManager
        carroProducteRepo.deleteByCarroId(carro.getIdCarrito());

        // 2. Neteja la llista en memòria per evitar que Hibernate entri en conflicte
        carro.getCarroProductes().clear(); 

        // 3. Reset del total
        carro.setTotal(0);

        // 4. Neteja context i guarda
        entityManager.flush();
        entityManager.clear();
        carroRepo.save(carro);
    }

}
