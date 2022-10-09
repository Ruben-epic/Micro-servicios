package com.example.carroservice.servicios;

import com.example.carroservice.entidades.Carro;
import com.example.carroservice.repositorio.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    public List<Carro> getAll(){
        return carroRepository.findAll();
    }

    public Carro getCarroByid(int id){
        return carroRepository.findById(id).orElse(null);
    }

    public Carro save(Carro carro){
        var nuevoCarro = carroRepository.save(carro);
        return nuevoCarro;
    }

    public List<Carro> byUsuarioId(int usuarioId){
        return carroRepository.findByUsuarioId(usuarioId);
    }
}