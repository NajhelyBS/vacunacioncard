package pe.edu.utp.vacunacioncard.service.auth;

import pe.edu.utp.vacunacioncard.model.auth.SesionUsuario;
import java.util.Optional;

public interface ISesionUsuarioService {

    SesionUsuario crearSesion(SesionUsuario sesion);

    Optional<SesionUsuario> obtenerPorToken(String token);

    void cerrarSesion(Long id);

    boolean verificarBloqueoCuenta(int intentosFallidos);
}
