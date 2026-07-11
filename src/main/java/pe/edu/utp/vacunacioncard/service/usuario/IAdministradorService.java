package pe.edu.utp.vacunacioncard.service.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.Administrador;
import java.util.List;
import java.util.Optional;

public interface IAdministradorService {

    /** Obtiene todos los administradores registrados. */
    List<Administrador> getAll();

    /** Busca un administrador por su identificador único. */
    Optional<Administrador> getById(Long id);

    /** Registra un nuevo administrador en el sistema. */
    Administrador create(Administrador administrador);

    /** Actualiza los datos de un administrador existente. */
    Administrador update(Administrador administrador);

    /** Elimina un administrador del sistema. */
    void deleteById(Long id);

    /** Lista los administradores asignados a un área institucional. */
    List<Administrador> findByArea(String area);
}