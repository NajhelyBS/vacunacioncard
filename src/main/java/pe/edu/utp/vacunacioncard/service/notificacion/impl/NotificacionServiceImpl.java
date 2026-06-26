package pe.edu.utp.vacunacioncard.service.notificacion.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.repository.notificacion.NotificacionRepository;
import pe.edu.utp.vacunacioncard.service.notificacion.INotificacionService;
import pe.edu.utp.vacunacioncard.service.patron.creacional.factorymethod.NotificacionFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements INotificacionService {
    private static final String ZONE_LIMA = "America/Lima";

    private final NotificacionRepository repo;

    @Override
    @Transactional
    public Notificacion enviarNotificacion(Notificacion notificacion) {
        log.info("Enviando notificación al usuario ID: {}", notificacion.getDestinatario().getId());
        try {
            notificacion.setFechaEnvio(LocalDateTime.now(ZoneId.of(ZONE_LIMA)));
            notificacion.setEstado("ENVIADA");
            Notificacion guardada = repo.save(notificacion);
            log.info("Notificación enviada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al enviar notificación", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> listarPorUsuario(Long usuarioId) {
        log.info("Listando notificaciones del usuario ID: {}", usuarioId);
        return repo.findByDestinatarioId(usuarioId);
    }

    @Override
    @Transactional
    public void registrarNuevaAlerta(Usuario usuario, String mensaje) {
        log.info("Creando alerta para usuario ID: {}", usuario.getId());
        Notificacion nuevaAlerta = NotificacionFactory.crearNotificacion("ALERTA", usuario, mensaje);
        try {
            nuevaAlerta.setFechaEnvio(LocalDateTime.now(ZoneId.of(ZONE_LIMA)));
            nuevaAlerta.setEstado("ENVIADA");
            repo.save(nuevaAlerta);
            log.info("Alerta registrada para usuario ID: {}", usuario.getId());
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar alerta", e);
        }
    }

    @Override
    @Transactional
    public void registrarRecordatorioVacuna(Usuario usuario, RegistroVacuna registro, LocalDateTime fecha) {
        log.info("Creando recordatorio de vacuna para usuario ID: {}", usuario.getId());
        Notificacion recordatorio = NotificacionFactory.crearRecordatorio(usuario, registro, fecha);
        try {
            recordatorio.setFechaEnvio(LocalDateTime.now(ZoneId.of(ZONE_LIMA)));
            recordatorio.setEstado("PENDIENTE");
            repo.save(recordatorio);
            log.info("Recordatorio registrado para usuario ID: {}", usuario.getId());
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar recordatorio de vacuna", e);
        }
    }
}
