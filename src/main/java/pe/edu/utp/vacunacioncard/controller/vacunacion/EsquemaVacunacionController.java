package pe.edu.utp.vacunacioncard.controller.vacunacion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<EsquemaVacunacion>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un esquema por ID", operationId = "getEsquemaById")
    public ResponseEntity<EsquemaVacunacion> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Lista esquemas asignados a un paciente", operationId = "getEsquemasByPaciente")
    public ResponseEntity<List<EsquemaVacunacion>> getByPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.findByPatient(pacienteId));
    }

    @PostMapping
    @Operation(summary = "Asigna un nuevo esquema de vacunación", operationId = "createEsquema")
    public ResponseEntity<EsquemaVacunacion> create(@RequestBody EsquemaVacunacion esquema) {
        EsquemaVacunacion creado = service.create(esquema);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un esquema existente", operationId = "updateEsquema")
    public ResponseEntity<EsquemaVacunacion> update(@PathVariable Long id, @RequestBody EsquemaVacunacion esquema) {
        esquema.setId(id);
        return ResponseEntity.ok(service.update(esquema));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un esquema de vacunación", operationId = "deleteEsquema")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}