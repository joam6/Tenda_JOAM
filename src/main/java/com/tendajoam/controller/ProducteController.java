package com.tendajoam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tendajoam.entity.product.Producte;
import com.tendajoam.service.interfaces.ProducteService;

@RestController
@RequestMapping("/api/productes")
public class ProducteController {

    private final ProducteService producteService;

    public ProducteController(ProducteService producteService) {
        this.producteService = producteService;
    }

    @GetMapping
    public ResponseEntity<List<Producte>> getAll() {
        return ResponseEntity.ok(producteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producte> getById(@PathVariable String id) {
        return producteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producte> create(@RequestBody Producte producte) {
        return ResponseEntity.ok(producteService.save(producte));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producte> update(@PathVariable String id, @RequestBody Producte producte) {
        producte.setIdProducte(id);
        return ResponseEntity.ok(producteService.save(producte));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        producteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
