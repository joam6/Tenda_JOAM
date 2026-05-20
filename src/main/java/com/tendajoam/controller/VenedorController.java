package com.tendajoam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tendajoam.entity.users.Venedor;
import com.tendajoam.service.interfaces.VenedorService;

@RestController
@RequestMapping("/api/venedors")
public class VenedorController {

    private final VenedorService venedorService;

    public VenedorController(VenedorService venedorService) {
        this.venedorService = venedorService;
    }

    @GetMapping
    public ResponseEntity<List<Venedor>> getAll() {
        return ResponseEntity.ok(venedorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venedor> getById(@PathVariable String id) {
        return venedorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Venedor> create(@RequestBody Venedor venedor) {
        return ResponseEntity.ok(venedorService.save(venedor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venedor> update(@PathVariable String id, @RequestBody Venedor venedor) {
        venedor.setIdUsuari(id);
        return ResponseEntity.ok(venedorService.save(venedor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        venedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}