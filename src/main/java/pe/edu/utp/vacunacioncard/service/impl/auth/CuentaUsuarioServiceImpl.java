package pe.edu.utp.vacunacioncard.service.impl.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario;
import pe.edu.utp.vacunacioncard.repository.auth.CuentaUsuarioRepository;
import pe.edu.utp.vacunacioncard.service.auth.ICuentaUsuarioService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de los servicios para la gestión de cuentas de usuario.
 * Aplica inyección por constructor mediante RequiredArgsConstructor.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CuentaUsuarioServiceImpl implements ICuentaUsuarioService {

    private final CuentaUsuarioRepository repo;

    /**
     * Obtiene la lista de todas las cuentas registradas.
     * @return Lista de CuentaUsuario.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CuentaUsuario> listarTodas() {
        return repo.findAll();
    }

    /**
     * Busca una cuenta por su identificador único.
     * @param id El ID de la cuenta.
     * @return Un Optional con la cuenta si existe.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CuentaUsuario> buscarPorId(Long id) {
        return repo.findById(id);
    }

    /**
     * Registra una nueva cuenta en el sistema con manejo de excepciones.
     * @param cuenta El objeto cuenta a persistir.
     * @return La cuenta guardada o null si hubo un error.
     */
    @Override
    @Transactional
    public CuentaUsuario registrar(CuentaUsuario cuenta) {
        try {
            return repo.save(cuenta);
        } catch (DataAccessException e) {
            log.error("Error al registrar la cuenta: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Elimina una cuenta del sistema.
     * @param id El ID de la cuenta a eliminar.
     */
    @Override
    @Transactional
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}