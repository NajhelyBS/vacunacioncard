package pe.edu.utp.vacunacioncard.controller.vacunacion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.dto.vacunacion.RegistroVacunaRequest;
import pe.edu.utp.vacunacioncard.dto.vacunacion.RegistroVacunaResponse;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.service.vacunacion.IRegistroVacunaService;

import java.util.List;

@RestController
@RequestMapping("/api/registros-vacuna")
@Tag(name = "Registros de Vacuna", description = "Aplicación y consulta de dosis aplicadas")
@RequiredArgsConstructor
public class RegistroVacunaController {

    private final IRegistroVacunaService service;

    @GetMapping
    @Operation(summary = "Lista todos los registros de vacuna", operationId = "getAllRegistrosVacuna")
    public ResponseEntity<List<RegistroVacunaResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(RegistroVacunaResponse::from).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un registro de vacuna por ID", operationId = "getRegistroVacunaById")
    public ResponseEntity<RegistroVacunaResponse> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(r -> ResponseEntity.ok(RegistroVacunaResponse.from(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/enfermero/{enfermeroId}")
    @Operation(summary = "Lista registros aplicados por un enfermero", operationId = "getRegistrosByEnfermero")
    public ResponseEntity<List<RegistroVacunaResponse>> getByEnfermero(@PathVariable Long enfermeroId) {
        return ResponseEntity.ok(service.findByNurse(enfermeroId).stream().map(RegistroVacunaResponse::from).toList());
    }

    @PostMapping
    @Operation(summary = "Registra la aplicación de una nueva dosis", operationId = "createRegistroVacuna")
    public ResponseEntity<RegistroVacunaResponse> create(@RequestBody RegistroVacunaRequest request) {
        RegistroVacuna creado = service.create(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(RegistroVacunaResponse.from(creado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un registro de vacuna existente", operationId = "updateRegistroVacuna")
    public ResponseEntity<RegistroVacunaResponse> update(@PathVariable Long id, @RequestBody RegistroVacunaRequest request) {
        RegistroVacuna registro = request.toEntity();
        registro.setId(id);
        return ResponseEntity.ok(RegistroVacunaResponse.from(service.update(registro)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un registro de vacuna (corrección de errores)", operationId = "deleteRegistroVacuna")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}