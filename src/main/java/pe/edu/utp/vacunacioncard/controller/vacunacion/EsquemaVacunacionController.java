package pe.edu.utp.vacunacioncard.controller.vacunacion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.dto.vacunacion.EsquemaVacunacionResponse;
import pe.edu.utp.vacunacioncard.model.vacunacion.EsquemaVacunacion;
import pe.edu.utp.vacunacioncard.service.vacunacion.IEsquemaVacunacionService;

import java.util.List;

@RestController
@RequestMapping("/api/esquemas")
@Tag(name = "Esquemas de Vacunación", description = "Cronogramas de dosis asignados a pacientes")
@RequiredArgsConstructor
public class EsquemaVacunacionController {

    private final IEsquemaVacunacionService service;

    @GetMapping
    @Operation(summary = "Lista todos los esquemas", operationId = "getAllEsquemas")
    public ResponseEntity<List<EsquemaVacunacionResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(EsquemaVacunacionResponse::from).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un esquema por ID", operationId = "getEsquemaById")
    public ResponseEntity<EsquemaVacunacionResponse> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(e -> ResponseEntity.ok(EsquemaVacunacionResponse.from(e)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Lista esquemas asignados a un paciente", operationId = "getEsquemasByPaciente")
    public ResponseEntity<List<EsquemaVacunacionResponse>> getByPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.findByPatient(pacienteId).stream().map(EsquemaVacunacionResponse::from).toList());
    }

    @PostMapping
    @Operation(summary = "Asigna un nuevo esquema de vacunación", operationId = "createEsquema")
    public ResponseEntity<EsquemaVacunacionResponse> create(@RequestBody EsquemaVacunacion esquema) {
        EsquemaVacunacion creado = service.create(esquema);
        return ResponseEntity.status(HttpStatus.CREATED).body(EsquemaVacunacionResponse.from(creado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un esquema existente", operationId = "updateEsquema")
    public ResponseEntity<EsquemaVacunacionResponse> update(@PathVariable Long id, @RequestBody EsquemaVacunacion esquema) {
        esquema.setId(id);
        return ResponseEntity.ok(EsquemaVacunacionResponse.from(service.update(esquema)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un esquema de vacunación", operationId = "deleteEsquema")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}