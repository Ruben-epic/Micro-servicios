package usuario.service.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import usuario.service.entidades.Usuario;
import usuario.service.modelos.Carro;
import usuario.service.modelos.Moto;
import usuario.service.servicio.UsuarioService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    ResponseEntity<List<Usuario>> listarUsuario(){
        var usuarios = usuarioService.getAll();
        return usuarios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    ResponseEntity<Usuario> obtenerUsuario(@PathVariable int id){
        var usuario = usuarioService.getUsuarioById(id);
        return usuario == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(usuario);
    }

    @PostMapping
    ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
        var nuevoUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @GetMapping("/carros/{usuarioId}")
    ResponseEntity<List<Carro>> listarCarros(@PathVariable int usuarioId){
        var usuario = usuarioService.getUsuarioById(usuarioId);
        if (usuario == null) return ResponseEntity.notFound().build();

        var carros = usuarioService.getCarro(usuarioId);
        return ResponseEntity.ok(carros);

    }

    @GetMapping("/motos/{usuarioId}")
    ResponseEntity<List<Moto>> listarMoto(@PathVariable int usuarioId){
        var usuario = usuarioService.getUsuarioById(usuarioId);
        if (usuario == null) return ResponseEntity.notFound().build();

        var moto = usuarioService.getMoto(usuarioId);
        return ResponseEntity.ok(moto);
    }

    @PostMapping("/carro/{usuarioId}")
    ResponseEntity<Carro> guardarCarro(@PathVariable int usuarioId, @RequestBody Carro carro){
        var nuevoCarro = usuarioService.saveCarro(usuarioId, carro);
        return ResponseEntity.ok(nuevoCarro);
    }
    @PostMapping("/moto/{usuarioId}")
    ResponseEntity<Moto> guardarMoto(@PathVariable int usuarioId, @RequestBody Moto moto){
        moto.setUsuarioId(usuarioId);
        var nuevoCarro = usuarioService.saveMoto(usuarioId, moto);
        return ResponseEntity.ok(nuevoCarro);
    }

    @GetMapping("/todos/{usuarioId}")
    ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable int usuarioId){
        var resultado = usuarioService.getUsuarioAndVehiculos(usuarioId);
        return ResponseEntity.ok(resultado);
    }
}
