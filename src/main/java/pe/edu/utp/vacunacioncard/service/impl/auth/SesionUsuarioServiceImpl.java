package pe.edu.utp.vacunacioncard.service.impl.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.auth.SesionUsuario;
import pe.edu.utp.vacunacioncard.repository.auth.SesionUsuarioRepository;
import pe.edu.utp.vacunacioncard.service.auth.ISesionUsuarioService;

import java.util.Optional;

/**
 * Implementación de los servicios de gestión de sesiones.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SesionUsuarioServiceImpl implements ISesionUsuarioService {

    private final SesionUsuarioRepository repo;

    /**
     * Registra una nueva sesión en el sistema.
     * @param sesion El objeto sesión a persistir.
     * @return La sesión guardada o null si falló.
     */
    @Override
    @Transactional
    public SesionUsuario crearSesion(SesionUsuario sesion) {
        try {
            return repo.save(sesion);
        } catch (DataAccessException e) {
            log.error("Error al crear sesión: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Busca una sesión activa mediante su token.
     * @param token El token de acceso.
     * @return Un Optional con la sesión encontrada.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SesionUsuario> obtenerPorToken(String token) {
        return repo.findByToken(token);
    }

    /**
     * Inactiva una sesión por su ID.
     * @param id Identificador de la sesión.
     */
    @Override
    @Transactional
    public void cerrarSesion(Long id) {
        repo.findById(id).ifPresent(s -> {
            s.setActiva(false);
            repo.save(s);
        });
    }
}