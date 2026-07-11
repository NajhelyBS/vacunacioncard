package pe.edu.utp.vacunacioncard.controller.salud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.dto.salud.CondicionMedicaRequest;
import pe.edu.utp.vacunacioncard.dto.salud.CondicionMedicaResponse;
import pe.edu.utp.vacunacioncard.model.salud.CondicionMedica;
import pe.edu.utp.vacunacioncard.service.salud.ICondicionMedicaService;

import java.util.List;

@RestController
@RequestMapping("/api/condiciones-medicas")
@Tag(name = "Condiciones Médicas", description = "Catálogo de condiciones médicas (CIE-10)")
@RequiredArgsConstructor
public class CondicionMedicaController {

    private final ICondicionMedicaService service;

    @GetMapping
    @Operation(summary = "Lista todas las condiciones médicas", operationId = "getAllCondicionesMedicas")
    public ResponseEntity<List<CondicionMedicaResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(CondicionMedicaResponse::from).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una condición médica por ID", operationId = "getCondicionMedicaById")
    public ResponseEntity<CondicionMedicaResponse> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(c -> ResponseEntity.ok(CondicionMedicaResponse.from(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cie10/{codigo}")
    @Operation(summary = "Busca una condición médica por código CIE-10", operationId = "getCondicionMedicaByIcd10")
    public ResponseEntity<CondicionMedicaResponse> getByIcd10(@PathVariable String codigo) {
        return service.findByIcd10Code(codigo)
                .map(c -> ResponseEntity.ok(CondicionMedicaResponse.from(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registra una nueva condición médica", operationId = "createCondicionMedica")
    public ResponseEntity<CondicionMedicaResponse> create(@RequestBody CondicionMedicaRequest request) {
        CondicionMedica creada = service.create(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(CondicionMedicaResponse.from(creada));
    }
}