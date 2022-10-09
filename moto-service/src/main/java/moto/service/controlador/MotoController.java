package moto.service.controlador;

import moto.service.entidades.Moto;
import moto.service.servicios.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moto")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    ResponseEntity<List<Moto>> listarMotos(){
        var motos = motoService.getAll();
        return motos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(motos);
    }

    @GetMapping("{id}")
    ResponseEntity<Moto> obtenerMoto(@PathVariable int id){
        var moto = motoService.getCarroByid(id);
        return moto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(moto);
    }

    @PostMapping
    ResponseEntity<Moto> guardarMoto(@RequestBody Moto moto){
        var nuevoMoto = motoService.save(moto);
        return ResponseEntity.ok(nuevoMoto);
    }

    @GetMapping("/usuario/{usuarioId}")
    ResponseEntity<List<Moto>> listarMotosPorUsuarioId(@PathVariable int usuarioId){
        var motos = motoService.byUsuarioId(usuarioId);
        return motos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(motos);
    }

}
