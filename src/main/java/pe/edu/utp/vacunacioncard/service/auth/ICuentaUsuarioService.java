package pe.edu.utp.vacunacioncard.service.auth;

import pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario;
import java.util.List;
import java.util.Optional;

/**
 * Contrato del servicio para la gestión de cuentas de usuario.
 * Define las operaciones de negocio disponibles sobre la entidad {@link CuentaUsuario}.
 */
public interface ICuentaUsuarioService {

    /**
     * Obtiene todas las cuentas de usuario registradas en el sistema.
     *
     * @return {@link List} con todas las entidades {@link CuentaUsuario}.
     */
    List<CuentaUsuario> getAll();

    /**
     * Busca una cuenta de usuario por su identificador único.
     *
     * @param id Identificador único de la cuenta.
     * @return Un {@link Optional} con la {@link CuentaUsuario} si existe, o vacío en caso contrario.
     */
    Optional<CuentaUsuario> getById(Long id);

    /**
     * Busca una cuenta de usuario por su nombre de usuario (username).
     * Resulta esencial para los flujos de autenticación e inicio de sesión.
     *
     * @param username Nombre de usuario único asociado a la cuenta.
     * @return Un {@link Optional} con la {@link CuentaUsuario} si existe, o vacío en caso contrario.
     */
    Optional<CuentaUsuario> findByUsername(String username);

    /**
     * Registra una nueva cuenta de usuario en el sistema.
     *
     * @param cuenta Entidad {@link CuentaUsuario} con los datos a persistir.
     * @return La entidad {@link CuentaUsuario} persistida con su identificador asignado.
     */
    CuentaUsuario create(CuentaUsuario cuenta);

    /**
     * Elimina una cuenta de usuario a partir de su identificador único.
     *
     * @param id Identificador único de la cuenta a eliminar.
     */
    void deleteById(Long id);
}
