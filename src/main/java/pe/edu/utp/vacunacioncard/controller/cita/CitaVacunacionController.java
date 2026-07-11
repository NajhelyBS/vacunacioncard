package pe.edu.utp.vacunacioncard.controller.cita;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.dto.cita.CitaVacunacionRequest;
import pe.edu.utp.vacunacioncard.dto.cita.CitaVacunacionResponse;
import pe.edu.utp.vacunacioncard.exception.ServiceException;
import pe.edu.utp.vacunacioncard.model.cita.CitaVacunacion;
import pe.edu.utp.vacunacioncard.repository.campania.CentroVacunacionRepository;
import pe.edu.utp.vacunacioncard.repository.usuario.PacienteRepository;
import pe.edu.utp.vacunacioncard.repository.vacunacion.VacunaRepository;
import pe.edu.utp.vacunacioncard.service.cita.ICitaVacunacionService;


import java.util.List;


@RestController
@RequestMapping("/api/citas")
@Tag(name = "Citas de Vacunación", description = "Programación y consulta de citas de vacunación")
@RequiredArgsConstructor
public class CitaVacunacionController {


    private final ICitaVacunacionService service;
    private final PacienteRepository pacienteRepository;
    private final VacunaRepository vacunaRepository;
    private final CentroVacunacionRepository centroVacunacionRepository;


    @GetMapping
    @Operation(summary = "Lista todas las citas de vacunación", operationId = "getAllCitas")
    public ResponseEntity<List<CitaVacunacionResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(CitaVacunacionResponse::from).toList());
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una cita de vacunación por ID", operationId = "getCitaById")
    public ResponseEntity<CitaVacunacionResponse> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(c -> ResponseEntity.ok(CitaVacunacionResponse.from(c)))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Lista las citas de un paciente", operationId = "getCitasByPaciente")
    public ResponseEntity<List<CitaVacunacionResponse>> getByPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.findByPatient(pacienteId).stream().map(CitaVacunacionResponse::from).toList());
    }


    @GetMapping("/estado/{estado}")
    @Operation(summary = "Lista las citas según su estado", operationId = "getCitasByEstado")
    public ResponseEntity<List<CitaVacunacionResponse>> getByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.findByStatus(estado).stream().map(CitaVacunacionResponse::from).toList());
    }


    @PostMapping
    @Operation(summary = "Programa una nueva cita de vacunación", operationId = "createCita")
    public ResponseEntity<CitaVacunacionResponse> create(@RequestBody CitaVacunacionRequest request) {
        validarRelaciones(request);


        CitaVacunacion creada = service.create(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(CitaVacunacionResponse.from(creada));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Reprograma o actualiza una cita de vacunación existente", operationId = "updateCita")
    public ResponseEntity<CitaVacunacionResponse> update(@PathVariable Long id, @RequestBody CitaVacunacionRequest request) {
        validarRelaciones(request);


        CitaVacunacion cita = request.toEntity();
        cita.setId(id);
        return ResponseEntity.ok(CitaVacunacionResponse.from(service.update(cita)));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina (cancela definitivamente) una cita de vacunación", operationId = "deleteCita")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    private void validarRelaciones(CitaVacunacionRequest request) {
        if (request.pacienteId() == null || !pacienteRepository.existsById(request.pacienteId())) {
            throw new ServiceException("No existe el paciente con ID: " + request.pacienteId(), null);
        }
        if (request.vacunaId() == null || !vacunaRepository.existsById(request.vacunaId())) {
            throw new ServiceException("No existe la vacuna con ID: " + request.vacunaId(), null);
        }
        if (request.centroVacunacionId() != null
                && !centroVacunacionRepository.existsById(request.centroVacunacionId())) {
            throw new ServiceException("No existe el centro de vacunación con ID: " + request.centroVacunacionId(), null);
        }
    }
}
