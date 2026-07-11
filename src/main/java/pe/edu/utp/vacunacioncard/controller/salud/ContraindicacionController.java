package pe.edu.utp.vacunacioncard.controller.salud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<Contraindicacion>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/vacuna/{vacunaId}")
    @Operation(summary = "Lista contraindicaciones de una vacuna específica", operationId = "getContraindicacionesByVacuna")
    public ResponseEntity<List<Contraindicacion>> getByVacuna(@PathVariable Long vacunaId) {
        return ResponseEntity.ok(service.findByAffectedVaccine(vacunaId));
    }

    @PostMapping
    @Operation(summary = "Registra una nueva contraindicación", operationId = "createContraindicacion")
    public ResponseEntity<Contraindicacion> create(@RequestBody Contraindicacion contraindicacion) {
        Contraindicacion creada = service.create(contraindicacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }
}
