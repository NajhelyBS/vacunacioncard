package pe.edu.utp.vacunacioncard.controller.vacunacion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.dto.vacunacion.VacunaRequest;
import pe.edu.utp.vacunacioncard.dto.vacunacion.VacunaResponse;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;
import pe.edu.utp.vacunacioncard.service.vacunacion.IVacunaService;

import java.util.List;

@RestController
@RequestMapping("/api/vacunas")
@Tag(name = "Vacunas", description = "Catálogo de vacunas disponibles (consulta frecuente)")
@RequiredArgsConstructor
public class VacunaController {

    private final IVacunaService service;

    @GetMapping
    @Operation(summary = "Lista todas las vacunas", operationId = "getAllVacunas")
    public ResponseEntity<List<VacunaResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(VacunaResponse::from).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una vacuna por ID", operationId = "getVacunaById")
    public ResponseEntity<VacunaResponse> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(v -> ResponseEntity.ok(VacunaResponse.from(v)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/disponibles/{disponible}")
    @Operation(summary = "Filtra vacunas por disponibilidad", operationId = "getVacunasByAvailability")
    public ResponseEntity<List<VacunaResponse>> getByAvailability(@PathVariable boolean disponible) {
        return ResponseEntity.ok(service.findByAvailability(disponible).stream().map(VacunaResponse::from).toList());
    }

    @PostMapping
    @Operation(summary = "Registra una nueva vacuna en el catálogo (solo admin)", operationId = "createVacuna")
    public ResponseEntity<VacunaResponse> create(@RequestBody VacunaRequest request) {
        Vacuna creada = service.create(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(VacunaResponse.from(creada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una vacuna del catálogo (solo admin)", operationId = "updateVacuna")
    public ResponseEntity<VacunaResponse> update(@PathVariable Long id, @RequestBody VacunaRequest request) {
        Vacuna vacuna = request.toEntity();
        vacuna.setId(id);
        return ResponseEntity.ok(VacunaResponse.from(service.update(vacuna)));
    }
}