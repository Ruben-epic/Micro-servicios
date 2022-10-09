package moto.service.servicios;

import moto.service.entidades.Moto;
import moto.service.reporitorio.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    public List<Moto> getAll(){
        return motoRepository.findAll();
    }

    public Moto getCarroByid(int id){
        return motoRepository.findById(id).orElse(null);
    }

    public Moto save(Moto moto){
        var nuevoMoto = motoRepository.save(moto);
        return nuevoMoto;
    }

    public List<Moto> byUsuarioId(int usuarioId){
        return motoRepository.findByUsuarioId(usuarioId);
    }

}
