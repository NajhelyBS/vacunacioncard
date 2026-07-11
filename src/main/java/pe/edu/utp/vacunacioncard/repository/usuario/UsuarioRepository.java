package pe.edu.utp.vacunacioncard.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import java.util.Optional;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByDni(String dni);
    List<Usuario> findByActivo(boolean activo);
}

