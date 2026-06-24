package pe.edu.utp.vacunacioncard.service.impl.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.auth.Rol;
import pe.edu.utp.vacunacioncard.repository.auth.RolRepository;
import pe.edu.utp.vacunacioncard.service.auth.IRolService;

import java.util.List;

/**
 * Implementación del servicio para la gestión de roles.
 * Utiliza @RequiredArgsConstructor para la inyección de dependencias.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RolServiceImpl implements IRolService {

    private final RolRepository repo;

    /**
     * Recupera todos los roles maestros del sistema.
     * @return Lista de objetos Rol.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Rol> listarTodo() {
        return repo.findAll();
    }

    /**
     * Registra un nuevo rol con manejo de errores de persistencia.
     * @param rol El rol a registrar.
     * @return El objeto persistido o null en caso de error.
     */
    @Override
    @Transactional
    public Rol guardar(Rol rol) {
        try {
            return repo.save(rol);
        } catch (DataAccessException e) {
            log.error("Error al guardar el rol: {}", e.getMessage());
            return null;
        }
    }
}