package pe.edu.utp.vacunacioncard.controller.vacunacion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<Vacuna>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una vacuna por ID", operationId = "getVacunaById")
    public ResponseEntity<Vacuna> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/disponibles/{disponible}")
    @Operation(summary = "Filtra vacunas por disponibilidad", operationId = "getVacunasByAvailability")
    public ResponseEntity<List<Vacuna>> getByAvailability(@PathVariable boolean disponible) {
        return ResponseEntity.ok(service.findByAvailability(disponible));
    }

    @PostMapping
    @Operation(summary = "Registra una nueva vacuna en el catálogo (solo admin)", operationId = "createVacuna")
    public ResponseEntity<Vacuna> create(@RequestBody Vacuna vacuna) {
        Vacuna creada = service.create(vacuna);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una vacuna del catálogo (solo admin)", operationId = "updateVacuna")
    public ResponseEntity<Vacuna> update(@PathVariable Long id, @RequestBody Vacuna vacuna) {
        vacuna.setId(id);
        return ResponseEntity.ok(service.update(vacuna));
    }
}