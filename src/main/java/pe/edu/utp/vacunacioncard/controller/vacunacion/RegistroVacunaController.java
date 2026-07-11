package pe.edu.utp.vacunacioncard.controller.vacunacion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<RegistroVacuna>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un registro de vacuna por ID", operationId = "getRegistroVacunaById")
    public ResponseEntity<RegistroVacuna> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/enfermero/{enfermeroId}")
    @Operation(summary = "Lista registros aplicados por un enfermero", operationId = "getRegistrosByEnfermero")
    public ResponseEntity<List<RegistroVacuna>> getByEnfermero(@PathVariable Long enfermeroId) {
        return ResponseEntity.ok(service.findByNurse(enfermeroId));
    }

    @PostMapping
    @Operation(summary = "Registra la aplicación de una nueva dosis", operationId = "createRegistroVacuna")
    public ResponseEntity<RegistroVacuna> create(@RequestBody RegistroVacuna registro) {
        RegistroVacuna creado = service.create(registro);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un registro de vacuna existente", operationId = "updateRegistroVacuna")
    public ResponseEntity<RegistroVacuna> update(@PathVariable Long id, @RequestBody RegistroVacuna registro) {
        registro.setId(id);
        return ResponseEntity.ok(service.update(registro));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un registro de vacuna (corrección de errores)", operationId = "deleteRegistroVacuna")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}