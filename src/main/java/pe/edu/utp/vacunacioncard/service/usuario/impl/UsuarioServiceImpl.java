package pe.edu.utp.vacunacioncard.service.usuario.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.repository.usuario.UsuarioRepository;
import pe.edu.utp.vacunacioncard.service.usuario.IUsuarioService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarActivos() {
        log.info("Listando usuarios activos");
        return repo.findByActivo(true);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorDni(String dni) {
        log.info("Buscando usuario por DNI: {}", dni);
        return repo.findByDni(dni);
    }

    @Override
    @Transactional
    public void cambiarEstado(Long id) {
        log.info("Cambiando estado del usuario ID: {}", id);
        try {
            repo.findById(id).ifPresent(u -> {
                u.setActivo(!u.isActivo());
                repo.save(u);
                log.info("Usuario ID: {} estado cambiado a: {}", id, u.isActivo());
            });
        } catch (DataAccessException e) {
            throw new ServiceException("Error al cambiar estado del usuario ID: " + id, e);
        }
    }
}
