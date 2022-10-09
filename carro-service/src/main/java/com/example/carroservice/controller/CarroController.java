package com.example.carroservice.controller;

import com.example.carroservice.entidades.Carro;
import com.example.carroservice.servicios.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carro")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @GetMapping
    ResponseEntity<List<Carro>> listarCarros(){
        var carros = carroService.getAll();
        return carros.isEmpty() ? ResponseEntity.noContent().build() :ResponseEntity.ok(carros);
    }

    @GetMapping("{id}")
    ResponseEntity<Carro> obtenerCarro(@PathVariable int id){
        var carro = carroService.getCarroByid(id);
        return carro == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(carro);
    }

    @PostMapping
    ResponseEntity<Carro> guardarCarro(@RequestBody Carro carro){
        var nuevoCarro = carroService.save(carro);
        return ResponseEntity.ok(nuevoCarro);
    }

    @GetMapping("/usuario/{usuarioId}")
    ResponseEntity<List<Carro>> listarCarrosPorUsuarioId(@PathVariable int usuarioId){
        var carros = carroService.byUsuarioId(usuarioId);
        return carros.isEmpty()?ResponseEntity.noContent().build(): ResponseEntity.ok(carros);
    }
}
