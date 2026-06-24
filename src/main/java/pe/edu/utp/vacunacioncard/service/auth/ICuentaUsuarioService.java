package pe.edu.utp.vacunacioncard.service.auth;

import pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario;
import java.util.List;
import java.util.Optional;

public interface ICuentaUsuarioService {
    List<CuentaUsuario> listarTodas();
    Optional<CuentaUsuario> buscarPorId(Long id);
    CuentaUsuario registrar(CuentaUsuario cuenta);
    void eliminar(Long id);
}
