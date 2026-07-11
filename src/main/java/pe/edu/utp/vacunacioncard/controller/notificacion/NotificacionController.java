package pe.edu.utp.vacunacioncard.controller.notificacion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.dto.notificacion.AlertaRequest;
import pe.edu.utp.vacunacioncard.dto.notificacion.NotificacionResponse;
import pe.edu.utp.vacunacioncard.dto.notificacion.RecordatorioRequest;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.repository.usuario.UsuarioRepository;
import pe.edu.utp.vacunacioncard.repository.vacunacion.RegistroVacunaRepository;
import pe.edu.utp.vacunacioncard.service.notificacion.INotificacionService;

import java.util.List;

/**
 * Controlador REST para la gestión de notificaciones: consulta, marcado como leída,
 * y registro de alertas y recordatorios (donde actúa el patrón Factory Method).
 */
@RestController
@RequestMapping("/api/notificaciones")
@Tag(name = "Notificaciones", description = "Consulta de notificaciones y registro de alertas y recordatorios")
@RequiredArgsConstructor
public class NotificacionController {

    private final INotificacionService notificacionService;
    private final UsuarioRepository usuarioRepository;
    private final RegistroVacunaRepository registroVacunaRepository;

    /**
     * Lista las notificaciones de un usuario, opcionalmente filtradas por estado.
     *
     * @param usuarioId Identificador del usuario destinatario.
     * @param estado    Estado opcional para filtrar (por ejemplo, "PENDIENTE", "LEIDA").
     * @return Lista de notificaciones en formato público.
     */
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Lista las notificaciones de un usuario, filtrables por estado", operationId = "getNotificacionesByUser")
    public List<NotificacionResponse> findByUser(@PathVariable Long usuarioId,
                                                 @RequestParam(required = false) String estado) {
        List<Notificacion> notificaciones = (estado == null || estado.isBlank())
                ? notificacionService.findByUser(usuarioId)
                : notificacionService.findByUserAndStatus(usuarioId, estado);
        return notificaciones.stream()
                .map(NotificacionResponse::from)
                .toList();
    }

    /**
     * Marca una notificación como leída.
     *
     * @param id Identificador de la notificación.
     * @return La notificación actualizada.
     */
    @PatchMapping("/{id}/leida")
    @Operation(summary = "Marca una notificación como leída", operationId = "markNotificacionAsRead")
    public NotificacionResponse markAsRead(@PathVariable Long id) {
        return NotificacionResponse.from(notificacionService.markAsRead(id));
    }

    /**
     * Registra una alerta de sistema para un usuario (usa el Factory Method de sistema).
     *
     * @param request Datos de la alerta (usuario y mensaje).
     * @return Respuesta sin contenido (HTTP 201).
     */
    @PostMapping("/alerta")
    @Operation(summary = "Registra una alerta de sistema (Factory Method)", operationId = "registerAlerta")
    public ResponseEntity<Void> registerAlert(@RequestBody AlertaRequest request) {
        Usuario usuario = buscarUsuario(request.usuarioId());
        notificacionService.registerAlert(usuario, request.mensaje());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Registra un recordatorio de próxima dosis (usa el Factory Method de recordatorio).
     *
     * @param request Datos del recordatorio (usuario, registro de vacuna y fecha).
     * @return Respuesta sin contenido (HTTP 201).
     */
    @PostMapping("/recordatorio")
    @Operation(summary = "Registra un recordatorio de próxima dosis (Factory Method)", operationId = "registerRecordatorio")
    public ResponseEntity<Void> registerVaccineReminder(@RequestBody RecordatorioRequest request) {
        Usuario usuario = buscarUsuario(request.usuarioId());
        RegistroVacuna registro = registroVacunaRepository.findById(request.registroVacunaId())
                .orElseThrow(() -> new ServiceException(
                        "No existe el registro de vacuna ID: " + request.registroVacunaId(), null));
        notificacionService.registerVaccineReminder(usuario, registro, request.fecha());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private Usuario buscarUsuario(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ServiceException("No existe el usuario ID: " + usuarioId, null));
    }
}
