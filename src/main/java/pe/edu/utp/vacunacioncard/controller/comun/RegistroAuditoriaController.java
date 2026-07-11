package pe.edu.utp.vacunacioncard.controller.comun;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.dto.comun.RegistroAuditoriaRequest;
import pe.edu.utp.vacunacioncard.dto.comun.RegistroAuditoriaResponse;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.comun.RegistroAuditoria;
import pe.edu.utp.vacunacioncard.repository.usuario.UsuarioRepository;
import pe.edu.utp.vacunacioncard.service.comun.IRegistroAuditoriaService;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


/**
 * Expone el registro de auditoría del sistema. Es un log de solo lectura y escritura
 * (create): no existe update ni delete a propósito, ya que un registro de auditoría
 * no debería poder alterarse ni borrarse una vez creado.
 */
@RestController
@RequestMapping("/api/auditoria")
@Tag(name = "Registro de Auditoría", description = "Consulta y registro de acciones auditables del sistema")
@RequiredArgsConstructor
public class RegistroAuditoriaController {


    private final IRegistroAuditoriaService service;
    private final UsuarioRepository usuarioRepository;


    @GetMapping
    @Operation(summary = "Lista todos los registros de auditoría", operationId = "getAllRegistrosAuditoria")
    public ResponseEntity<List<RegistroAuditoriaResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(RegistroAuditoriaResponse::from).toList());
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un registro de auditoría por ID", operationId = "getRegistroAuditoriaById")
    public ResponseEntity<RegistroAuditoriaResponse> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(r -> ResponseEntity.ok(RegistroAuditoriaResponse.from(r)))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Lista la auditoría de un usuario específico", operationId = "getRegistrosAuditoriaByUsuario")
    public ResponseEntity<List<RegistroAuditoriaResponse>> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.findByUser(usuarioId).stream().map(RegistroAuditoriaResponse::from).toList());
    }


    @GetMapping("/entidad/{entidad}")
    @Operation(summary = "Lista la auditoría de una entidad afectada", operationId = "getRegistrosAuditoriaByEntidad")
    public ResponseEntity<List<RegistroAuditoriaResponse>> getByEntidad(@PathVariable String entidad) {
        return ResponseEntity.ok(service.findByAffectedEntity(entidad).stream().map(RegistroAuditoriaResponse::from).toList());
    }


    @PostMapping
    @Operation(summary = "Registra una nueva acción de auditoría", operationId = "createRegistroAuditoria")
    public ResponseEntity<RegistroAuditoriaResponse> create(
            @RequestBody RegistroAuditoriaRequest request,
            HttpServletRequest httpRequest) {


        RegistroAuditoria auditoria = request.toEntity();


        if (request.usuarioId() != null) {
            auditoria.setUsuario(usuarioRepository.findById(request.usuarioId())
                    .orElseThrow(() -> new ServiceException(
                            "No existe el usuario con ID: " + request.usuarioId(), null)));
        }


        auditoria.setFechaHora(LocalDateTime.now(ZoneId.of("America/Lima")));
        auditoria.setIpAddress(httpRequest.getRemoteAddr());


        RegistroAuditoria creado = service.create(auditoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(RegistroAuditoriaResponse.from(creado));
    }
}
