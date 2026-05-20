package com.tendajoam.controller;

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
    public void eliminar(@PathVariable String idCliente,
                         @PathVariable String idProducte) {
        carroService.eliminarProducte(idCliente, idProducte);
    }

    @DeleteMapping("/{idCliente}/buidar")
    public void buidar(@PathVariable String idCliente) {
        carroService.buidarCarro(idCliente);
    }
}
