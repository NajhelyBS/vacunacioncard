package pe.edu.utp.vacunacioncard.controller.vacunacion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.dto.vacunacion.CartillaVacunacionResponse;
import pe.edu.utp.vacunacioncard.model.vacunacion.CartillaVacunacion;
import pe.edu.utp.vacunacioncard.service.vacunacion.ICartillaVacunacionService;

import java.util.List;

@RestController
@RequestMapping("/api/cartillas")
@Tag(name = "Cartillas de Vacunación", description = "Historial digital de vacunación (carnet)")
@RequiredArgsConstructor
public class CartillaVacunacionController {

    private final ICartillaVacunacionService service;

    @GetMapping
    @Operation(summary = "Lista todas las cartillas", operationId = "getAllCartillas")
    public ResponseEntity<List<CartillaVacunacionResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(CartillaVacunacionResponse::from).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una cartilla por ID", operationId = "getCartillaById")
    public ResponseEntity<CartillaVacunacionResponse> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(c -> ResponseEntity.ok(CartillaVacunacionResponse.from(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Obtiene la cartilla de un paciente", operationId = "getCartillaByPaciente")
    public ResponseEntity<CartillaVacunacionResponse> getByPaciente(@PathVariable Long pacienteId) {
        return service.findByPatient(pacienteId)
                .map(c -> ResponseEntity.ok(CartillaVacunacionResponse.from(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/qr/{codigoQR}")
    @Operation(summary = "Obtiene una cartilla por código QR", operationId = "getCartillaByQr")
    public ResponseEntity<CartillaVacunacionResponse> getByQr(@PathVariable String codigoQR) {
        return service.findByQrCode(codigoQR)
                .map(c -> ResponseEntity.ok(CartillaVacunacionResponse.from(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crea una nueva cartilla de vacunación", operationId = "createCartilla")
    public ResponseEntity<CartillaVacunacionResponse> create(@RequestBody CartillaVacunacion cartilla) {
        CartillaVacunacion creada = service.create(cartilla);
        return ResponseEntity.status(HttpStatus.CREATED).body(CartillaVacunacionResponse.from(creada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una cartilla existente", operationId = "updateCartilla")
    public ResponseEntity<CartillaVacunacionResponse> update(@PathVariable Long id, @RequestBody CartillaVacunacion cartilla) {
        cartilla.setId(id);
        return ResponseEntity.ok(CartillaVacunacionResponse.from(service.update(cartilla)));
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(summary = "Desactiva una cartilla (baja lógica)", operationId = "deactivateCartilla")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}