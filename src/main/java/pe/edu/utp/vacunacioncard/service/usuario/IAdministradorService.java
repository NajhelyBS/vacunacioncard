package pe.edu.utp.vacunacioncard.service.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.Administrador;
import java.util.List;
import java.util.Optional;

public interface IAdministradorService {

    List<Administrador> listarTodos();

    Optional<Administrador> obtenerPorId(Long id);

    Administrador registrar(Administrador administrador);

    List<Administrador> listarPorArea(String area);
}
