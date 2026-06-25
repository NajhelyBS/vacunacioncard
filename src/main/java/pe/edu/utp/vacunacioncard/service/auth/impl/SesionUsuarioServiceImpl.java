package pe.edu.utp.vacunacioncard.service.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.auth.SesionUsuario;
import pe.edu.utp.vacunacioncard.repository.auth.SesionUsuarioRepository;
import pe.edu.utp.vacunacioncard.service.auth.ISesionUsuarioService;
import pe.edu.utp.vacunacioncard.service.patron.singleton.ConfiguracionSistema;

import java.util.Optional;

/**
 * Implementación de los servicios de gestión de sesiones.
 * Asegura el control de excepciones de persistencia y auditoría con Slf4j.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SesionUsuarioServiceImpl implements ISesionUsuarioService {

    private final SesionUsuarioRepository repo;

    /**
     * Registra una nueva sesión en el sistema con manejo de excepciones.
     * 
     * @param sesion El objeto sesión a persistir.
     * @return La sesión guardada o null si falló.
     */
    @Override
    @Transactional
    public SesionUsuario crearSesion(SesionUsuario sesion) {
        log.info("Registrando nueva sesión para la cuenta ID: {}",
                sesion.getCuenta() != null ? sesion.getCuenta().getId() : "N/A");
        try {
            SesionUsuario sesionGuardada = repo.save(sesion);
            log.info("Sesión creada exitosamente con ID: {}", sesionGuardada.getId());
            return sesionGuardada;
        } catch (DataAccessException e) {
            log.error("Error crítico de persistencia al crear la sesión: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Busca una sesión activa mediante su token.
     * 
     * @param token El token de acceso.
     * @return Un Optional con la sesión encontrada.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SesionUsuario> obtenerPorToken(String token) {
        log.info("Buscando sesión por token de acceso");
        return repo.findByToken(token);
    }

    /**
     * Inactiva una sesión por su ID de manera segura bajo bloque try-catch.
     * 
     * @param id Identificador de la sesión.
     */
    @Override
    @Transactional
    public void cerrarSesion(Long id) {
        log.info("Solicitud de cierre para la sesión ID: {}", id);
        try {
            repo.findById(id).ifPresent(s -> {
                s.setActiva(false);
                repo.save(s);
                log.info("Sesión ID: {} dada de baja correctamente", id);
            });
        } catch (DataAccessException e) {
            log.error("Error crítico al intentar cerrar la sesión ID {}: {}", id, e.getMessage());
        }
    }

    /***
     * Uso del patron Singleton
     * Verifica si la cuenta del usuario debe ser bloqueada según el número de intentos fallidos.
     * @param intentosFallidos Número de intentos fallidos de inicio de sesión.
     * @return true si la cuenta debe ser bloqueada, false en caso contrario.
     */
    @Override
    @Transactional
    public boolean verificarBloqueoCuenta(int intentosFallidos) {
        // Llamamos al Singleton para traer el límite global (que es 3)
        int limiteMaximo = ConfiguracionSistema.getInstancia().getMaxIntentosLogin();

        if (intentosFallidos >= limiteMaximo) {
            log.warn("La cuenta excede el límite de {} intentos permitidos", limiteMaximo);
            return true; // Cuenta bloqueada
        }
        return false;
    }

}
