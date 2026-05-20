package com.tendajoam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tendajoam.entity.users.Administrador;
import com.tendajoam.service.interfaces.AdministradorService;

@RestController
@RequestMapping("/api/admins")
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @GetMapping
    public ResponseEntity<List<Administrador>> getAll() {
        return ResponseEntity.ok(administradorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrador> getById(@PathVariable String id) {
        return administradorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Administrador> create(@RequestBody Administrador administrador) {
        return ResponseEntity.ok(administradorService.save(administrador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrador> update(@PathVariable String id, @RequestBody Administrador administrador) {
        administrador.setIdUsuari(id);
        return ResponseEntity.ok(administradorService.save(administrador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        administradorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}