package pe.edu.utp.vacunacioncard.service.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.auth.Rol;
import pe.edu.utp.vacunacioncard.repository.auth.RolRepository;
import pe.edu.utp.vacunacioncard.service.auth.IRolService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de roles de usuario.
 * Controla las operaciones de negocio, persistencia y consultas específicas de la entidad Rol.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RolServiceImpl implements IRolService {

    private final RolRepository repo;

    /**
     * Recupera un listado completo con todos los roles registrados en el sistema.
     *
     * @return {@link List} que contiene todas las entidades {@link Rol}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Rol> getAll() {
        log.info("Listando todos los roles");
        return repo.findAll();
    }
    
     /**
     * Guarda un nuevo rol o actualiza uno existente en el repositorio.
     *
     * @param rol Entidad {@link Rol} con la información a almacenar.
     * @return La entidad {@link Rol} guardada con su identificador generado.
     * @throws ServiceException Si ocurre un error de persistencia o acceso a la base de datos.
     */
    @Override
    @Transactional
    public Rol create(Rol rol) {
        log.info("Guardando rol: {}", rol.getNombre());
        try {
            Rol guardado = repo.save(rol);
            log.info("Rol guardado con ID: {}", guardado.getId());
            return guardado;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al guardar rol: " + rol.getNombre(), e);
        }
    }

    /**
     * Busca un rol en el sistema filtrándolo por su nombre único.
     *
     * @param nombre Nombre descriptivo del rol.
     * @return Un {@link Optional} con el {@link Rol} hallado, o vacío si no coincide ninguno.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Rol> findByName(String nombre) {
        log.info("Buscando rol por nombre: {}", nombre);
        return repo.findByNombre(nombre);
    }

    /**
     * Remueve un rol del sistema utilizando su identificador único.
     *
     * @param id Identificador único del rol que se desea eliminar.
     * @throws ServiceException Si se presenta un fallo de conectividad o restricción de datos al borrar.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando rol con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Rol eliminado con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar rol con ID: " + id, e);
        }
    }
}
