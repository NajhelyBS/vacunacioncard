package pe.edu.utp.vacunacioncard.controller.usuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import pe.edu.utp.vacunacioncard.service.usuario.IPacienteService;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@Tag(name = "Pacientes", description = "Gestión de pacientes del sistema")
@RequiredArgsConstructor
public class PacienteController {

    private final IPacienteService service;

    @GetMapping
    @Operation(summary = "Lista todos los pacientes", operationId = "getAllPacientes")
    public ResponseEntity<List<Paciente>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un paciente por ID", operationId = "getPacienteById")
    public ResponseEntity<Paciente> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/dni/{dni}")
    @Operation(summary = "Busca un paciente por DNI", operationId = "getPacienteByDni")
    public ResponseEntity<Paciente> getByDni(@PathVariable String dni) {
        return service.findByDni(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registra un nuevo paciente", operationId = "createPaciente")
    public ResponseEntity<Paciente> create(@RequestBody Paciente paciente) {
        Paciente creado = service.create(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un paciente existente", operationId = "updatePaciente")
    public ResponseEntity<Paciente> update(@PathVariable Long id, @RequestBody Paciente paciente) {
        paciente.setId(id);
        return ResponseEntity.ok(service.update(paciente));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un paciente por ID", operationId = "deletePaciente")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}