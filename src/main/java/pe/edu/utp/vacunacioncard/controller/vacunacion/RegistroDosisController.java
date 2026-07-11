package pe.edu.utp.vacunacioncard.controller.vacunacion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.dto.vacunacion.CartillaVacunacionResponse;
import pe.edu.utp.vacunacioncard.dto.vacunacion.RegistrarDosisRequest;
import pe.edu.utp.vacunacioncard.dto.vacunacion.RegistroVacunaResponse;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.service.patron.estructural.facade.CartillaVacunacionFacade;

@RestController
@RequestMapping("/api/registro-dosis")
@Tag(name = "Registro de Dosis (Facade)", description = "Orquesta el registro de dosis y su historial")
@RequiredArgsConstructor
public class RegistroDosisController {

    private final CartillaVacunacionFacade facade;

    @PostMapping
    @Operation(summary = "Registra una dosis y actualiza la cartilla del paciente", operationId = "registrarDosisFacade")
    public ResponseEntity<RegistroVacunaResponse> registrarDosis(@RequestBody RegistrarDosisRequest request) {
        RegistroVacuna guardado = facade.registrarDosisYActualizarCartilla(request.toEntity(), request.cartillaId());
        return ResponseEntity.status(HttpStatus.CREATED).body(RegistroVacunaResponse.from(guardado));
    }

    @GetMapping("/paciente/{pacienteId}/estado")
    @Operation(summary = "Consulta el estado de vacunación de un paciente", operationId = "consultarEstadoVacunacionFacade")
    public ResponseEntity<CartillaVacunacionResponse> consultarEstado(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(CartillaVacunacionResponse.from(facade.consultarEstadoVacunacion(pacienteId)));
    }
}