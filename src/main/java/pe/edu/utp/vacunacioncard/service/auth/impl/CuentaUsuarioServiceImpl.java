package pe.edu.utp.vacunacioncard.service.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario;
import pe.edu.utp.vacunacioncard.repository.auth.CuentaUsuarioRepository;
import pe.edu.utp.vacunacioncard.service.auth.ICuentaUsuarioService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para la gestión de cuentas de usuario.
 * Maneja la lógica de negocio y transacciones para la entidad CuentaUsuario.
 */
@Slf4j
@Service
@RequiredArgsConstructor

public class CuentaUsuarioServiceImpl implements ICuentaUsuarioService {

    private final CuentaUsuarioRepository repo;

    /**
     * Obtiene una lista con todas las cuentas de usuario registradas.
     * @return {@link List} de todas las entidades {@link CuentaUsuario}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CuentaUsuario> listarTodas() {
        log.info("Listando todas las cuentas de usuario");
        return repo.findAll();
    }

    /**
     * Busca una cuenta de usuario específica mediante su identificador único.
     * @param id Identificador único de la cuenta de usuario.
     * @return Un {@link Optional} que contiene la {@link CuentaUsuario} si se encuentra, 
     *         o un contenedor vacío si no existe.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CuentaUsuario> buscarPorId(Long id) {
        log.info("Buscando cuenta por ID: {}", id);
        return repo.findById(id);
    }

    /**
     * Registra o actualiza una cuenta de usuario en el sistema.
     *
     * @param cuenta Entidad {@link CuentaUsuario} con los datos a guardar.
     * @return La entidad {@link CuentaUsuario} persistida con su ID asignado.
     * @throws ServiceException Si ocurre un error a nivel de acceso a datos durante el registro.
     */
    @Override
    @Transactional
    public CuentaUsuario registrar(CuentaUsuario cuenta) {
        log.info("Registrando cuenta para username: {}", cuenta.getUsername());
        try {
            CuentaUsuario guardada = repo.save(cuenta);
            log.info("Cuenta registrada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar cuenta de usuario: " + cuenta.getUsername(), e);
        }
    }

    /**
     * Elimina una cuenta de usuario del sistema mediante su ID.
     *
     * @param id Identificador único de la cuenta a eliminar.
     * @throws ServiceException Si ocurre un error de acceso a datos al intentar eliminar el registro.
     */
    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando cuenta con ID: {}", id);
        try {
            repo.deleteById(id);
            log.info("Cuenta eliminada con ID: {}", id);
        } catch (DataAccessException e) {
            throw new ServiceException("Error al eliminar cuenta con ID: " + id, e);
        }
    }
}
