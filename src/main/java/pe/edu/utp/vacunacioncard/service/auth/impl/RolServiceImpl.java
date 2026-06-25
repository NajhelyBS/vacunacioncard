package pe.edu.utp.vacunacioncard.service.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.auth.Rol;
import pe.edu.utp.vacunacioncard.repository.auth.RolRepository;
import pe.edu.utp.vacunacioncard.service.auth.IRolService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de roles.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RolServiceImpl implements IRolService {

    private final RolRepository repo;

    /**
     * Recupera todos los roles maestros del sistema en modo lectura.
     * @return Lista de objetos Rol.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Rol> listarTodo() {
        log.info("Consultando el catálogo completo de roles del sistema");
        return repo.findAll();
    }

    /**
     * Registra un nuevo rol con manejo de errores de persistencia a nivel de base de datos.
     * @param rol El rol a registrar.
     * @return El objeto persistido o null en caso de error de concurrencia o esquema.
     */
    @Override
    @Transactional
    public Rol guardar(Rol rol) {
        log.info("Iniciando almacenamiento del rol: {}", rol.getNombre());
        try {
            Rol rolGuardado = repo.save(rol);
            log.info("Rol guardado exitosamente asignando ID: {}", rolGuardado.getId());
            return rolGuardado;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al intentar registrar el rol [{}]: {}", rol.getNombre(), e.getMessage());
            return null;
        }
    }

    /**
     * Busca un rol por su nombre en la base de datos de manera segura.
     * @param nombre El nombre del perfil.
     * @return Un contenedor Optional con la información encontrada.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Rol> buscarPorNombre(String nombre) {
        log.info("Solicitando búsqueda de rol por nombre clave: {}", nombre);
        return repo.findByNombre(nombre);
    }
}
