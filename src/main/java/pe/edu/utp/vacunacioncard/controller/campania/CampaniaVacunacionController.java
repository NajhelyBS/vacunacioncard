package pe.edu.utp.vacunacioncard.controller.campania;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.dto.campania.CampaniaVacunacionRequest;
import pe.edu.utp.vacunacioncard.dto.campania.CampaniaVacunacionResponse;
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
    public ResponseEntity<List<CampaniaVacunacionResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(CampaniaVacunacionResponse::from).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una campaña por ID", operationId = "getCampaniaById")
    public ResponseEntity<CampaniaVacunacionResponse> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(c -> ResponseEntity.ok(CampaniaVacunacionResponse.from(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Filtra campañas por estado", operationId = "getCampaniasByStatus")
    public ResponseEntity<List<CampaniaVacunacionResponse>> getByStatus(@PathVariable String estado) {
        return ResponseEntity.ok(service.findByStatus(estado).stream().map(CampaniaVacunacionResponse::from).toList());
    }

    @PostMapping
    @Operation(summary = "Crea una nueva campaña de vacunación", operationId = "createCampania")
    public ResponseEntity<CampaniaVacunacionResponse> create(@RequestBody CampaniaVacunacionRequest request) {
        CampaniaVacunacion creada = service.create(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(CampaniaVacunacionResponse.from(creada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza el progreso de una campaña", operationId = "updateCampania")
    public ResponseEntity<CampaniaVacunacionResponse> update(@PathVariable Long id, @RequestBody CampaniaVacunacionRequest request) {
        CampaniaVacunacion campania = request.toEntity();
        campania.setId(id);
        return ResponseEntity.ok(CampaniaVacunacionResponse.from(service.update(campania)));
    }
}
