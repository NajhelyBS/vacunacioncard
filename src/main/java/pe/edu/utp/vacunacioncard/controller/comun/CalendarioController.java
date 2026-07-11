package pe.edu.utp.vacunacioncard.controller.comun;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.dto.comun.CalendarioRequest;
import pe.edu.utp.vacunacioncard.dto.comun.CalendarioResponse;
import pe.edu.utp.vacunacioncard.model.comun.Calendario;
import pe.edu.utp.vacunacioncard.service.comun.ICalendarioService;


import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/calendario")
@Tag(name = "Calendario", description = "Gestión de días hábiles y feriados del sistema")
@RequiredArgsConstructor
public class CalendarioController {


    private final ICalendarioService service;


    @GetMapping("/fecha/{fecha}")
    @Operation(summary = "Obtiene el registro de calendario de una fecha específica", operationId = "getCalendarioByFecha")
    public ResponseEntity<CalendarioResponse> getByFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return service.findByDate(fecha)
                .map(c -> ResponseEntity.ok(CalendarioResponse.from(c)))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/habiles")
    @Operation(summary = "Lista los días según su disponibilidad hábil", operationId = "getCalendarioByAvailability")
    public ResponseEntity<List<CalendarioResponse>> getByAvailability(@RequestParam boolean esHabil) {
        return ResponseEntity.ok(service.findByAvailability(esHabil).stream().map(CalendarioResponse::from).toList());
    }


    @PostMapping
    @Operation(summary = "Registra un nuevo día en el calendario (hábil o feriado)", operationId = "createCalendario")
    public ResponseEntity<CalendarioResponse> create(@RequestBody CalendarioRequest request) {
        Calendario creado = service.create(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(CalendarioResponse.from(creado));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un registro de calendario", operationId = "deleteCalendario")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
