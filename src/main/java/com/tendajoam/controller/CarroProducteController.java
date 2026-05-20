package com.tendajoam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tendajoam.entity.cart.CarroProducte;
import com.tendajoam.entity.cart.CarroProducteId;
import com.tendajoam.service.interfaces.CarroProducteService;

@RestController
@RequestMapping("/api/carro-producte")
public class CarroProducteController {

    private final CarroProducteService carroProducteService;

    public CarroProducteController(CarroProducteService carroProducteService) {
        this.carroProducteService = carroProducteService;
    }

    @GetMapping
    public ResponseEntity<List<CarroProducte>> getAll() {
        return ResponseEntity.ok(carroProducteService.findAll());
    }

    @GetMapping("/{carroId}/{producteId}")
    public ResponseEntity<CarroProducte> getById(
            @PathVariable String carroId,
            @PathVariable String producteId) {

        CarroProducteId id = new CarroProducteId(carroId, producteId);

        return carroProducteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CarroProducte> create(@RequestBody CarroProducte cp) {
        return ResponseEntity.ok(carroProducteService.save(cp));
    }

    @DeleteMapping("/{carroId}/{producteId}")
    public ResponseEntity<Void> delete(
            @PathVariable String carroId,
            @PathVariable String producteId) {

        carroProducteService.delete(new CarroProducteId(carroId, producteId));
        return ResponseEntity.noContent().build();
    }
}
