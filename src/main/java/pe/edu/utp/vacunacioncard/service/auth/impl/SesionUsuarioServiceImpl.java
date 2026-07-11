package pe.edu.utp.vacunacioncard.service.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.auth.SesionUsuario;
import pe.edu.utp.vacunacioncard.repository.auth.SesionUsuarioRepository;
import pe.edu.utp.vacunacioncard.service.auth.ISesionUsuarioService;
import pe.edu.utp.vacunacioncard.service.patron.singleton.ConfiguracionSistema;

import java.util.Optional;

/**
 * Implementación del servicio para el control de sesiones de usuario.
 * Gestiona el ciclo de vida de las sesiones (creación, búsqueda, cierre) y la 
 * validación de bloqueo basada en configuraciones del sistema.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SesionUsuarioServiceImpl implements ISesionUsuarioService {

    private final SesionUsuarioRepository repo;

    /**
     * Registra una nueva sesión de usuario en el sistema.
     *
     * @param sesion Entidad {@link SesionUsuario} con los datos de autenticación y token.
     * @return La entidad {@link SesionUsuario} persistida con su ID generado.
     * @throws ServiceException Si ocurre un fallo en el acceso a datos al intentar almacenar la sesión.
     */
    @Override
    @Transactional
    public SesionUsuario createSession(SesionUsuario sesion) {
        log.info("Creando sesión para cuenta ID: {}", 
            sesion.getCuenta() != null ? sesion.getCuenta().getId() : "N/A"); 
        try {
            SesionUsuario guardada = repo.save(sesion);
            log.info("Sesión creada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al crear sesión de usuario", e);
        }
    }

    /**
     * Recupera una sesión activa o histórica mediante su token de autenticación.
     *
     * @param token Cadena de texto única que identifica la sesión (ej. JWT o UUID).
     * @return Un {@link Optional} que contiene la {@link SesionUsuario} si el token existe, 
     *         o un contenedor vacío si no hay coincidencias.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SesionUsuario> findByToken(String token) {
        log.info("Buscando sesión por token");
        return repo.findByToken(token);
    }

     /**
     * Invalida de forma lógica una sesión de usuario modificando su estado a inactiva.
     *
     * @param id Identificador único de la sesión que se desea cerrar.
     * @throws ServiceException Si se genera un error de persistencia al actualizar el estado de la sesión.
     */
    @Override
    @Transactional
    public void closeSession(Long id) {
        log.info("Cerrando sesión ID: {}", id);
        try {
            repo.findById(id).ifPresent(s -> {
                s.setActiva(false);
                repo.save(s);
                log.info("Sesión cerrada ID: {}", id);
            });
        } catch (DataAccessException e) {
            throw new ServiceException("Error al cerrar sesión ID: " + id, e);
        }
    }

    /**
     * Evalúa si una cuenta debe ser bloqueada comparando los intentos actuales con el límite configurado.
     * Este método hace uso de la clase {@link ConfiguracionSistema} mediante el patrón de diseño 
     * Singleton para extraer los parámetros globales de autenticación.     *
     * @param intentosFallidos Cantidad de intentos erróneos acumulados por el usuario.
     * @return {@code true} si los intentos igualan o superan el límite permitido, requiriendo bloqueo; 
     *         {@code false} en caso contrario.
     */
    @Override
    @Transactional
    public boolean isAccountLocked(int intentosFallidos) {
        int limiteMaximo = ConfiguracionSistema.getInstancia().getMaxIntentosLogin();

        if (intentosFallidos >= limiteMaximo) {
            log.warn("Cuenta excede el límite de {} intentos permitidos", limiteMaximo);
            return true;
        }
        return false;
    }
}
