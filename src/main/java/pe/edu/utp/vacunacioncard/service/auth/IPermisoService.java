package pe.edu.utp.vacunacioncard.service.auth;

import pe.edu.utp.vacunacioncard.model.auth.Permiso;
import java.util.List;

public interface IPermisoService {
    List<Permiso> listarTodos();
    Permiso guardar(Permiso permiso);
}