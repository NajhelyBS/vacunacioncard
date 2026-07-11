package pe.edu.utp.vacunacioncard.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.usuario.Administrador;
import java.util.List;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    List<Administrador> findByAreaIgnoreCase(String area);
}
