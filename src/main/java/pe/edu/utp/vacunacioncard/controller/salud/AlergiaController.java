package pe.edu.utp.vacunacioncard.controller.salud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.model.salud.Alergia;
import pe.edu.utp.vacunacioncard.service.salud.IAlergiaService;

import java.util.List;

@RestController
@RequestMapping("/api/alergias")
@Tag(name = "Alergias", description = "Catálogo de alergias para validación clínica")
@RequiredArgsConstructor
public class AlergiaController {

    private final IAlergiaService service;

    @GetMapping
    @Operation(summary = "Lista todas las alergias", operationId = "getAllAlergias")
    public ResponseEntity<List<Alergia>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una alergia por ID", operationId = "getAlergiaById")
    public ResponseEntity<Alergia> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/severidad/{severidad}")
    @Operation(summary = "Filtra alergias por severidad", operationId = "getAlergiasBySeverity")
    public ResponseEntity<List<Alergia>> getBySeverity(@PathVariable String severidad) {
        return ResponseEntity.ok(service.findBySeverity(severidad));
    }

    @PostMapping
    @Operation(summary = "Registra una nueva alergia en el catálogo", operationId = "createAlergia")
    public ResponseEntity<Alergia> create(@RequestBody Alergia alergia) {
        Alergia creada = service.create(alergia);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }
}