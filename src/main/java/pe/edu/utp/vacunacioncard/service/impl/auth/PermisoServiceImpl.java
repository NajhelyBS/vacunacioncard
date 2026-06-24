package pe.edu.utp.vacunacioncard.service.impl.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.auth.Permiso;
import pe.edu.utp.vacunacioncard.repository.auth.PermisoRepository;
import pe.edu.utp.vacunacioncard.service.auth.IPermisoService;

import java.util.List;

/**
 * Servicio para la gestión de permisos del sistema.
 * Utiliza inyección por constructor recomendada.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermisoServiceImpl implements IPermisoService {

    private final PermisoRepository repo;

    /**
     * Recupera todos los permisos configurados.
     * @return Lista de Permiso.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Permiso> listarTodos() {
        return repo.findAll();
    }

    /**
     * Registra un permiso en la base de datos maestra.
     * @param permiso Objeto a guardar.
     * @return El permiso guardado o null si ocurre un error.
     */
    @Override
    @Transactional
    public Permiso guardar(Permiso permiso) {
        try {
            return repo.save(permiso);
        } catch (DataAccessException e) {
            log.error("Error al persistir el permiso: {}", e.getMessage());
            return null;
        }
    }
}