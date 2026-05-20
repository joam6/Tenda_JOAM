package com.tendajoam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tendajoam.entity.users.Usuari;
import com.tendajoam.service.interfaces.UsuariService;

@RestController
@RequestMapping("/api/usuaris")
public class UsuariController {

    private final UsuariService usuariService;

    public UsuariController(UsuariService usuariService) {
        this.usuariService = usuariService;
    }

    @GetMapping
    public ResponseEntity<List<Usuari>> getAll() {
        return ResponseEntity.ok(usuariService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuari> getById(@PathVariable String id) {
        return usuariService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuari> create(@RequestBody Usuari usuari) {
        return ResponseEntity.ok(usuariService.save(usuari));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuari> update(@PathVariable String id, @RequestBody Usuari usuari) {
        usuari.setIdUsuari(id);
        return ResponseEntity.ok(usuariService.save(usuari));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        usuariService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
