package pe.edu.utp.vacunacioncard.service.auth;

import pe.edu.utp.vacunacioncard.model.auth.Rol;
import java.util.List;
import java.util.Optional;

public interface IRolService {

    List<Rol> listarTodo();

    Rol guardar(Rol rol);

    Optional<Rol> buscarPorNombre(String nombre);

    void eliminar(Long id);
}
