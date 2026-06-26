package pe.edu.utp.vacunacioncard.service.notificacion;

import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;

import java.time.LocalDateTime;
import java.util.List;

public interface INotificacionService {

    Notificacion enviarNotificacion(Notificacion notificacion);

    List<Notificacion> listarPorUsuario(Long usuarioId);

    void registrarNuevaAlerta(Usuario usuario, String mensaje);

    void registrarRecordatorioVacuna(Usuario usuario, RegistroVacuna registro, LocalDateTime fecha);
}
