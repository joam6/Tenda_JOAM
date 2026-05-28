package com.tendajoam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tendajoam.entity.order.Comanda;
import com.tendajoam.service.interfaces.ComandaService;

@RestController
@RequestMapping("/api/comandes")
public class ComandaController {

    private final ComandaService comandaService;

    public ComandaController(ComandaService comandaService) {
        this.comandaService = comandaService;
    }

    @PostMapping("/afegir")
    public ResponseEntity<?> afegirComanda(@RequestBody Comanda comanda) {
        // 1. Cercar l'últim ID per generar el següent
        String ultimId = comandaService.findLastId(); // Has de crear aquest mètode al repository
        int numero = (ultimId != null) ? Integer.parseInt(ultimId.replace("coman", "")) + 1 : 1;
        String nouId = String.format("coman%04d", numero);
        
        comanda.setIdComanda(nouId);
        comandaService.save(comanda);
        return ResponseEntity.ok("Comanda creada: " + nouId);
    }
    
    @PostMapping("/comprar/{idCliente}")
    public ResponseEntity<Comanda> comprar(@PathVariable String idCliente) {
        return ResponseEntity.ok(comandaService.comprar(idCliente));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Comanda> getById(@PathVariable String id) {
        return comandaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Comanda> create(@RequestBody Comanda comanda) {
        return ResponseEntity.ok(comandaService.save(comanda));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comanda> update(@PathVariable String id, @RequestBody Comanda comanda) {
        comanda.setIdComanda(id);
        return ResponseEntity.ok(comandaService.save(comanda));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        comandaService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/client/{idCliente}")
    public ResponseEntity<List<Comanda>> getByCliente(@PathVariable String idCliente) {
        return ResponseEntity.ok(comandaService.findByCliente(idCliente));
    }
}
