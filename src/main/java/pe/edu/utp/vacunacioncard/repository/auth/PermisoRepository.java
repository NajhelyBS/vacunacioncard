package pe.edu.utp.vacunacioncard.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.vacunacioncard.model.auth.Permiso;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    Permiso findByCodigo(String codigo);
}
