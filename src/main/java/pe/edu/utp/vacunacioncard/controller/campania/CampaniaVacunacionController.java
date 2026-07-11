package pe.edu.utp.vacunacioncard.controller.campania;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.model.campania.CampaniaVacunacion;
import pe.edu.utp.vacunacioncard.service.campania.ICampaniaVacunacionService;

import java.util.List;

@RestController
@RequestMapping("/api/campanias")
@Tag(name = "Campañas de Vacunación", description = "Campañas masivas por grupo objetivo")
@RequiredArgsConstructor
public class CampaniaVacunacionController {

    private final ICampaniaVacunacionService service;

    @GetMapping
    @Operation(summary = "Lista todas las campañas", operationId = "getAllCampanias")
    public ResponseEntity<List<CampaniaVacunacion>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una campaña por ID", operationId = "getCampaniaById")
    public ResponseEntity<CampaniaVacunacion> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Filtra campañas por estado", operationId = "getCampaniasByStatus")
    public ResponseEntity<List<CampaniaVacunacion>> getByStatus(@PathVariable String estado) {
        return ResponseEntity.ok(service.findByStatus(estado));
    }

    @PostMapping
    @Operation(summary = "Crea una nueva campaña de vacunación", operationId = "createCampania")
    public ResponseEntity<CampaniaVacunacion> create(@RequestBody CampaniaVacunacion campania) {
        CampaniaVacunacion creada = service.create(campania);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza el progreso de una campaña", operationId = "updateCampania")
    public ResponseEntity<CampaniaVacunacion> update(@PathVariable Long id, @RequestBody CampaniaVacunacion campania) {
        campania.setId(id);
        return ResponseEntity.ok(service.update(campania));
    }
}
