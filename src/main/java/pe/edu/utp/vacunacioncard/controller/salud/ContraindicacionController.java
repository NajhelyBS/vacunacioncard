package pe.edu.utp.vacunacioncard.controller.salud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.dto.salud.ContraindicacionRequest;
import pe.edu.utp.vacunacioncard.dto.salud.ContraindicacionResponse;
import pe.edu.utp.vacunacioncard.model.salud.Contraindicacion;
import pe.edu.utp.vacunacioncard.service.salud.IContraindicacionService;

import java.util.List;

@RestController
@RequestMapping("/api/contraindicaciones")
@Tag(name = "Contraindicaciones", description = "Validación clínica de seguridad antes de aplicar dosis")
@RequiredArgsConstructor
public class ContraindicacionController {

    private final IContraindicacionService service;

    @GetMapping
    @Operation(summary = "Lista todas las contraindicaciones", operationId = "getAllContraindicaciones")
    public ResponseEntity<List<ContraindicacionResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(ContraindicacionResponse::from).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una contraindicación por ID", operationId = "getContraindicacionById")
    public ResponseEntity<ContraindicacionResponse> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(c -> ResponseEntity.ok(ContraindicacionResponse.from(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/vacuna/{vacunaId}")
    @Operation(summary = "Lista contraindicaciones de una vacuna específica", operationId = "getContraindicacionesByVacuna")
    public ResponseEntity<List<ContraindicacionResponse>> getByVacuna(@PathVariable Long vacunaId) {
        return ResponseEntity.ok(service.findByAffectedVaccine(vacunaId).stream().map(ContraindicacionResponse::from).toList());
    }

    @PostMapping
    @Operation(summary = "Registra una nueva contraindicación", operationId = "createContraindicacion")
    public ResponseEntity<ContraindicacionResponse> create(@RequestBody ContraindicacionRequest request) {
        Contraindicacion creada = service.create(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(ContraindicacionResponse.from(creada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una contraindicación existente", operationId = "updateContraindicacion")
    public ResponseEntity<ContraindicacionResponse> update(@PathVariable Long id, @RequestBody ContraindicacionRequest request) {
        if (service.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Contraindicacion actualizada = service.update(request.toEntity(id));
        return ResponseEntity.ok(ContraindicacionResponse.from(actualizada));
    }
}
