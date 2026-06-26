package pe.edu.utp.vacunacioncard.service.auth;

import pe.edu.utp.vacunacioncard.model.auth.Permiso;
import java.util.List;
import java.util.Optional;

public interface IPermisoService {

    List<Permiso> listarTodos();

    Permiso guardar(Permiso permiso);

    Optional<Permiso> buscarPorCodigo(String codigo);

    void eliminar(Long id);
}
