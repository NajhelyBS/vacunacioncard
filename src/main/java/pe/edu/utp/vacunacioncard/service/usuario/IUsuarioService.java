package pe.edu.utp.vacunacioncard.service.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> listarActivos();
    Optional<Usuario> buscarPorDni(String dni);
    void cambiarEstado(Long id);
}