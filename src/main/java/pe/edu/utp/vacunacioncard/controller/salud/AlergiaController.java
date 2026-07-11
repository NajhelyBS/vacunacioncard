package pe.edu.utp.vacunacioncard.controller.salud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.dto.salud.AlergiaResponse;
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
    public ResponseEntity<List<AlergiaResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(AlergiaResponse::from).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una alergia por ID", operationId = "getAlergiaById")
    public ResponseEntity<AlergiaResponse> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(a -> ResponseEntity.ok(AlergiaResponse.from(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/severidad/{severidad}")
    @Operation(summary = "Filtra alergias por severidad", operationId = "getAlergiasBySeverity")
    public ResponseEntity<List<AlergiaResponse>> getBySeverity(@PathVariable String severidad) {
        return ResponseEntity.ok(service.findBySeverity(severidad).stream().map(AlergiaResponse::from).toList());
    }

    @PostMapping
    @Operation(summary = "Registra una nueva alergia en el catálogo", operationId = "createAlergia")
    public ResponseEntity<AlergiaResponse> create(@RequestBody Alergia alergia) {
        Alergia creada = service.create(alergia);
        return ResponseEntity.status(HttpStatus.CREATED).body(AlergiaResponse.from(creada));
    }
}