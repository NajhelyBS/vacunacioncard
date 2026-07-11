package pe.edu.utp.vacunacioncard.controller.cita;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.dto.cita.HorarioRequest;
import pe.edu.utp.vacunacioncard.dto.cita.HorarioResponse;
import pe.edu.utp.vacunacioncard.model.cita.DiaSemana;
import pe.edu.utp.vacunacioncard.model.cita.Horario;
import pe.edu.utp.vacunacioncard.service.cita.IHorarioService;


import java.util.List;


@RestController
@RequestMapping("/api/horarios")
@Tag(name = "Horarios", description = "Gestión de los horarios de atención para vacunación")
@RequiredArgsConstructor
public class HorarioController {


    private final IHorarioService service;


    @GetMapping
    @Operation(summary = "Lista todos los horarios", operationId = "getAllHorarios")
    public ResponseEntity<List<HorarioResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(HorarioResponse::from).toList());
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un horario por ID", operationId = "getHorarioById")
    public ResponseEntity<HorarioResponse> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(h -> ResponseEntity.ok(HorarioResponse.from(h)))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/dia/{diaSemana}")
    @Operation(summary = "Lista los horarios de un día de la semana", operationId = "getHorariosByDia")
    public ResponseEntity<List<HorarioResponse>> getByDia(@PathVariable DiaSemana diaSemana) {
        return ResponseEntity.ok(service.findByDay(diaSemana).stream().map(HorarioResponse::from).toList());
    }


    @PostMapping
    @Operation(summary = "Registra un nuevo horario de atención", operationId = "createHorario")
    public ResponseEntity<HorarioResponse> create(@RequestBody HorarioRequest request) {
        Horario creado = service.create(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(HorarioResponse.from(creado));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un horario de atención existente", operationId = "updateHorario")
    public ResponseEntity<HorarioResponse> update(@PathVariable Long id, @RequestBody HorarioRequest request) {
        Horario horario = request.toEntity();
        horario.setId(id);
        return ResponseEntity.ok(HorarioResponse.from(service.update(horario)));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un horario de atención", operationId = "deleteHorario")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

