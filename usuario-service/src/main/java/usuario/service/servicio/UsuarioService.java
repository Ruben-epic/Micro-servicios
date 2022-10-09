package usuario.service.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import usuario.service.entidades.Usuario;
import usuario.service.feighclient.CarroFeignClient;
import usuario.service.feighclient.MotoFeignClient;
import usuario.service.modelos.Carro;
import usuario.service.modelos.Moto;
import usuario.service.repositorio.UsuarioRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarroFeignClient carroFeignClient;

    @Autowired
    private MotoFeignClient motoFeignClient;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(int id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario){
        var nuevoUsuario = usuarioRepository.save(usuario);
        return nuevoUsuario;
    }

    // Mediante Rest Template
    public List<Carro> getCarro(int usuarioId){
        var carros = restTemplate.getForObject("http://localhost:8002/carro/usuario/" + usuarioId, List.class);
        return carros;
    }

    public List<Moto> getMoto(int usuarioId){
        var motos = restTemplate.getForObject("http://localhost:8003/moto/usuario/" + usuarioId, List.class);
        return motos;
    }

    //Feign Client

    public Carro saveCarro(int usuariId, Carro carro){
        carro.setUsuarioId(usuariId);
        var nuevoCarro = carroFeignClient.save(carro);
        return nuevoCarro;
    }

    public Moto saveMoto(int usuarioId, Moto moto){
        moto.setUsuarioId(usuarioId);
        var nuevaMoto = motoFeignClient.save(moto);
        return nuevaMoto;
    }

    public Map<String, Object> getUsuarioAndVehiculos(int usuarioId){
        Map<String, Object> resultado = new HashMap<>();

        var usuario = usuarioRepository.findById(usuarioId).orElse(null);
        var condicionUsuario = usuario == null ? resultado.put("Mensaje", "El usuario no exites") : resultado.put("Usuario", usuario);

        var carros = carroFeignClient.getCarros(usuarioId);
        var condicionCarro = carros.isEmpty() ? resultado.put("Carros", "El usuario no tiene carros") : resultado.put("Carros", carros);

        var motos = motoFeignClient.getMoto(usuarioId);
        var condicionMoto = motos.isEmpty() ? resultado.put("Motos", "El usuario no tiene Motos") : resultado.put("Motos", motos);

        return resultado;
    }
    ///
}
