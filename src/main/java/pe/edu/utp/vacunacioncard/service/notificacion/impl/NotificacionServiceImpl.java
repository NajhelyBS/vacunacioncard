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
import pe.edu.utp.vacunacioncard.service.patron.factorymethod.NotificacionCreator;
import pe.edu.utp.vacunacioncard.service.patron.factorymethod.RecordatorioNotificacionCreator;
import pe.edu.utp.vacunacioncard.service.patron.factorymethod.SistemaNotificacionCreator;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Implementación del servicio para la gestión y envío de notificaciones.
 * Administra el ciclo de vida de las alertas del sistema y recordatorios de dosis,
 * centralizando la lógica horaria local de Lima y aplicando patrones creacionales.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements INotificacionService {

    /** Zona horaria oficial para el registro y envío de alertas. */
    private static final String ZONE_LIMA = "America/Lima";

    /** Estado asignado a una notificación despachada. */
    private static final String ESTADO_ENVIADA = "ENVIADA";

    /** Estado asignado a una notificación ya consultada por el destinatario. */
    private static final String ESTADO_LEIDA = "LEIDA";

    private final NotificacionRepository repo;

    /**
     * Procesa y despacha de forma inmediata una notificación configurada previamente.
     * Modifica internamente el estado de la notificación a "ENVIADA" y estampa
     * la marca de tiempo bajo la zona horaria {@code America/Lima}.
     *
     * @param notificacion Entidad {@link Notificacion} con la información base y el destinatario asignado.
     * @return La entidad {@link Notificacion} guardada en el histórico con su ID generado.
     * @throws ServiceException Si ocurre un fallo en el acceso a datos al intentar almacenar el envío.
     */
    @Override
    @Transactional
    public Notificacion sendNotification(Notificacion notificacion) {
        log.info("Enviando notificación al usuario ID: {}", notificacion.getDestinatario().getId());
        try {
            notificacion.setFechaEnvio(LocalDateTime.now(ZoneId.of(ZONE_LIMA)));
            notificacion.setEstado(ESTADO_ENVIADA);
            Notificacion guardada = repo.save(notificacion);
            log.info("Notificación enviada con ID: {}", guardada.getId());
            return guardada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al enviar notificación", e);
        }
    }

    /**
     * Recupera el buzón histórico de todas las notificaciones destinadas a un usuario en particular.
     *
     * @param usuarioId Identificador único del usuario receptor.
     * @return {@link List} que contiene las entidades {@link Notificacion} dirigidas a dicho usuario.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> findByUser(Long usuarioId) {
        log.info("Listando notificaciones del usuario ID: {}", usuarioId);
        return repo.findByDestinatarioId(usuarioId);
    }

    /**
     * Recupera las notificaciones de un usuario filtradas por su estado (p. ej. "ENVIADA", "PENDIENTE").
     * La comparación del estado es insensible a mayúsculas y minúsculas.
     *
     * @param usuarioId Identificador único del usuario receptor.
     * @param estado    Estado por el cual filtrar el buzón del usuario.
     * @return {@link List} de {@link Notificacion} del usuario que coinciden con el estado solicitado.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> findByUserAndStatus(Long usuarioId, String estado) {
        log.info("Listando notificaciones del usuario ID: {} con estado: {}", usuarioId, estado);
        return repo.findByDestinatarioIdAndEstadoIgnoreCase(usuarioId, estado);
    }

    /**
     * Marca una notificación como leída cambiando su estado a "LEIDA".
     *
     * @param notificacionId Identificador único de la notificación a marcar.
     * @return La entidad {@link Notificacion} actualizada.
     * @throws ServiceException Si la notificación no existe o si ocurre un fallo al persistir el cambio.
     */
    @Override
    @Transactional
    public Notificacion markAsRead(Long notificacionId) {
        log.info("Marcando como leída la notificación ID: {}", notificacionId);
        try {
            Notificacion notificacion = repo.findById(notificacionId)
                    .orElseThrow(() -> new ServiceException(
                            "No existe una notificación con ID: " + notificacionId, null));
            notificacion.setEstado(ESTADO_LEIDA);
            Notificacion actualizada = repo.save(notificacion);
            log.info("Notificación ID: {} actualizada a LEIDA", notificacionId);
            return actualizada;
        } catch (DataAccessException e) {
            throw new ServiceException("Error al actualizar el estado de la notificación", e);
        }
    }

    /**
     * Construye y persiste una nueva alerta informativa dirigida a un usuario específico.
     * Este método hace uso del patrón de diseño <b>Factory Method</b> instanciando la clase
     *
     * @param usuario Entidad {@link Usuario} que recibirá el mensaje de alerta.
     * @param mensaje Contenido textual de la advertencia o evento del sistema.
     * @throws ServiceException Si ocurre una excepción de persistencia durante el guardado de la alerta.
     */
    @Override
    @Transactional
    public void registerAlert(Usuario usuario, String mensaje) {
        log.info("Registrando alerta de sistema para usuario ID: {}", usuario.getId());
        try {
            NotificacionCreator creador = new SistemaNotificacionCreator("ALERTA");
            Notificacion nuevaAlerta = creador.generarNotificacion(usuario, mensaje);
            repo.save(nuevaAlerta);
            log.info("Alerta registrada exitosamente.");
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar alerta", e);
        }
    }

    /**
     * Registra un recordatorio cronológico para la aplicación de una dosis o vacuna pendiente.
     * Este método implementa el patrón de diseño <b>Factory Method</b> a través de la clase
     * {@link RecordatorioNotificacionCreator}, encapsulando las dependencias del registro médico
     * y la fecha programada. El registro se guarda inicialmente con el estado "PENDIENTE".
     *
     * @param usuario  Entidad {@link Usuario} que debe acudir a la vacunación.
     * @param registro Entidad {@link RegistroVacuna} que contiene el detalle de la dosis correspondiente.
     * @param fecha    Fecha y hora estimadas en las que se debe gatillar el recordatorio.
     * @throws ServiceException Si ocurre una falla en la base de datos al almacenar la planificación.
     */
    @Override
    @Transactional
    public void registerVaccineReminder(Usuario usuario, RegistroVacuna registro, LocalDateTime fecha) {
        log.info("Creando recordatorio de vacuna para usuario ID: {}", usuario.getId());
        try {
            // 1. EMPLEACIÓN DEL PATRÓN: Instanciamos el Creator de Recordatorios con sus dependencias requeridas
            NotificacionCreator creator = new RecordatorioNotificacionCreator(registro, fecha);
            // 2. Invocamos el método del patrón para generar el producto abstracto Notificacion
            Notificacion recordatorio = creator.generarNotificacion(usuario, null);
            // 3. Persistimos el registro médico en el repositorio
            repo.save(recordatorio);
            log.info("Recordatorio médico registrado de forma idónea para usuario ID: {}", usuario.getId());
        } catch (DataAccessException e) {
            throw new ServiceException("Error al registrar recordatorio de vacuna", e);
        }
    }

}
