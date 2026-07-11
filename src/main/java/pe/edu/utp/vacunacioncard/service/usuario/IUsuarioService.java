package pe.edu.utp.vacunacioncard.service.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    /**
     * Obtiene todos los usuarios activos del sistema, sin distinguir su rol.
     * @return lista de usuarios activos.
     */
    List<Usuario> getActiveUsers();

    /**
     * Busca un usuario por su Documento Nacional de Identidad.
     * @param dni DNI del usuario.
     * @return el usuario si existe.
     */
    Optional<Usuario> findByDni(String dni);

    /**
     * Alterna el estado activo/inactivo de un usuario (baja lógica).
     * @param id identificador del usuario.
     */
    void toggleStatus(Long id);
}