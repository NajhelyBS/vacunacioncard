package pe.edu.utp.vacunacioncard.service.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.auth.Permiso;
import pe.edu.utp.vacunacioncard.repository.auth.PermisoRepository;
import pe.edu.utp.vacunacioncard.service.auth.IPermisoService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de permisos del sistema.
 * Maneja la lógica de negocio, búsquedas por código y persistencia de la entidad Permiso.
 */

@Slf4j
@Service
@RequiredArgsConstructor

public class PermisoServiceImpl implements IPermisoService {

    private final PermisoRepository repo;

    /**
     * Obtiene una lista con todos los permisos registrados en el sistema.
     *
     * @return {@link List} de todas las entidades {@link Permiso}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Permiso> getAll() {
        log.info("Listando todos los permisos");
        return repo.findAll();
    }

     /**
     * Registra o actualiza un permiso en el sistema.
     *
     * @param permiso Entidad {@link Permiso} con los datos a persistir.
     * @return La entidad {@link Permiso} guardada con su identificador asignado.
     * @throws ServiceException Si ocurre un error a nivel de base de datos durante el guardado.
     */
    @Override
    @Transactional
    public Permiso create(Permiso permiso) {
        log.info("Guardando permiso con código: {}", permiso.getCodigo());
        try {
            Permiso guardado = repo.save(permiso);
            log.info("Permiso guardado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar permiso: " + permiso.getCodigo(), e);
        }
    }

     /**
     * Busca un permiso en el sistema filtrándolo por su código único.
     *
     * @param codigo Código identificador del permiso.
     * @return Un {@link Optional} con el {@link Permiso} hallado, o vacío si no coincide ninguno.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Permiso> findByCode(String codigo) {
        log.info("Buscando permiso por código: {}", codigo);
        return repo.findByCodigo(codigo);
    }

    /**
     * Elimina un permiso del sistema utilizando su identificador único.
     *
     * @param id Identificador único del permiso a eliminar.
     * @throws ServiceException Si ocurre un error de acceso a datos al intentar eliminar el registro.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando permiso con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Permiso eliminado con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar permiso con ID: " + id, e);
        }
    }
}
