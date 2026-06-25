package pe.edu.utp.vacunacioncard.service.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.auth.Permiso;
import pe.edu.utp.vacunacioncard.repository.auth.PermisoRepository;
import pe.edu.utp.vacunacioncard.service.auth.IPermisoService;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de permisos del sistema.
 * Implementa try-catch, rastreabilidad con Slf4j y control transaccional de Spring.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class PermisoServiceImpl implements IPermisoService {

    private final PermisoRepository repo;

    /**
     * Recupera todos los permisos configurados de forma segura en modo lectura.
     * @return Lista de Permiso.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Permiso> listarTodos() {
        log.info("Iniciando consulta global de permisos del sistema");
        return repo.findAll();
    }

    /**
     * Registra un permiso en la base de datos maestra con manejo estricto de excepciones.
     * @param permiso Objeto a guardar.
     * @return El permiso guardado o null si ocurre un error.
     */
    @Override
    @Transactional
    public Permiso guardar(Permiso permiso) {
        log.info("Intentando persistir nuevo permiso con código: {}", permiso.getCodigo());
        try {
            Permiso permisoGuardado = repo.save(permiso);
            log.info("Permiso guardado exitosamente con ID: {}", permisoGuardado.getId());
            return permisoGuardado;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al guardar el permiso [{}]: {}", permiso.getCodigo(), e.getMessage());
            return null;
        }
    }

    /**
     * Busca un permiso en la base de datos por su código de negocio único.
     * @param codigo El código del permiso.
     * @return Un Optional con el permiso encontrado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Permiso> buscarPorCodigo(String codigo) {
        log.info("Buscando permiso por código único: {}", codigo);
        return repo.findByCodigo(codigo);
    }
}
