package pe.edu.utp.vacunacioncard.service.usuario.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.repository.usuario.UsuarioRepository;
import pe.edu.utp.vacunacioncard.service.usuario.IUsuarioService;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicios para la gestión de usuarios base.
 * Aplica el principio de inyección por constructor mediante @RequiredArgsConstructor [6].
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository repo;

    /**
     * Recupera la lista de todos los usuarios registrados.
     * @return Lista de usuarios.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarActivos() {
        return repo.findAll();
    }

    /**
     * Busca un usuario por su número de DNI.
     * @param dni Documento nacional de identidad.
     * @return Un Optional con el usuario si existe.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorDni(String dni) {
        return repo.findByDni(dni);
    }

    /**
     * Cambia el estado de actividad de un usuario (Inactivación lógica).
     * @param id Identificador del usuario.
     */
    @Override
    @Transactional
    public void cambiarEstado(Long id) {
        repo.findById(id).ifPresent(u -> {
            u.setActivo(!u.isActivo());
            repo.save(u);
            log.info("Estado del usuario ID {} cambiado a {}", id, u.isActivo());
        });
    }
}