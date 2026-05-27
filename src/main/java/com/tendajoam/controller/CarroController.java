package com.tendajoam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tendajoam.entity.cart.Carro;
import com.tendajoam.service.interfaces.CarroService;

@RestController
@RequestMapping("/api/carro")
public class CarroController {

    private final CarroService carroService;

    public CarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @GetMapping("/{idCliente}")
    public Carro getCarro(@PathVariable String idCliente) {
        return carroService.getCarroByCliente(idCliente);
    }

    @PostMapping("/{idCliente}/afegir")
    public void afegir(@PathVariable String idCliente,
                       @RequestParam String idProducte,
                       @RequestParam(defaultValue = "1") int quantitat) {
        carroService.afegirProducte(idCliente, idProducte, quantitat);
    }

    @DeleteMapping("/{idCliente}/eliminar/{idProducte}")
    public ResponseEntity<Void> eliminarDelCarro(
            @PathVariable String idCliente, 
            @PathVariable String idProducte) {
        
        System.out.println("DEBUG: Entrant a eliminar producte: " + idProducte + " del client: " + idCliente);
        
        carroService.eliminarProducte(idCliente, idProducte);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idCliente}/buidar")
    public void buidar(@PathVariable String idCliente) {
        carroService.buidarCarro(idCliente);
    }
}
