package pe.edu.utp.vacunacioncard.service.notificacion.impl;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.service.notificacion.INotificacionService;
import pe.edu.utp.vacunacioncard.service.patron.creacional.factorymethod.NotificacionFactory;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import java.util.List;
import java.util.ArrayList;

@Service
@Slf4j
public class NotificacionServiceImpl implements INotificacionService {

    @Override
    public void enviarNotificacion(Notificacion notificacion) {
        log.info("Enviando notificación: {}", notificacion.getMensaje());
    }

    @Override
    public List<Notificacion> listarPorUsuario(Long usuarioId) {
        return new ArrayList<>();
    }

    public void registrarNuevaAlerta(Usuario usuario, String mensaje) {
        Notificacion nuevaAlerta = NotificacionFactory.crearNotificacion("ALERTA", usuario, mensaje);
                enviarNotificacion(nuevaAlerta);
    }
}