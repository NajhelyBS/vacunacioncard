package pe.edu.utp.vacunacioncard.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.dto.auth.CuentaResponse;
import pe.edu.utp.vacunacioncard.service.auth.ICuentaUsuarioService;

import java.util.List;

/**
 * Controlador REST para la gestión de cuentas de usuario (consulta y baja).
 * El alta se realiza a través de {@link AuthController#register}.
 */
@RestController
@RequestMapping("/api/cuentas")
@Tag(name = "Cuentas", description = "Consulta y baja de cuentas de usuario")
@RequiredArgsConstructor
public class CuentaUsuarioController {

    private final ICuentaUsuarioService cuentaService;

    /**
     * Lista todas las cuentas de usuario registradas.
     *
     * @return Lista de cuentas en formato público.
     */
    @GetMapping
    @Operation(summary = "Lista todas las cuentas de usuario", operationId = "getAllCuentas")
    public List<CuentaResponse> getAll() {
        return cuentaService.getAll().stream()
                .map(CuentaResponse::from)
                .toList();
    }

    /**
     * Obtiene una cuenta por su identificador.
     *
     * @param id Identificador de la cuenta.
     * @return La cuenta si existe (HTTP 200), o HTTP 404 en caso contrario.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una cuenta por ID", operationId = "getCuentaById")
    public ResponseEntity<CuentaResponse> getById(@PathVariable Long id) {
        return cuentaService.getById(id)
                .map(cuenta -> ResponseEntity.ok(CuentaResponse.from(cuenta)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Elimina una cuenta por su identificador.
     *
     * @param id Identificador de la cuenta a eliminar.
     * @return Respuesta sin contenido (HTTP 204).
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una cuenta por ID", operationId = "deleteCuentaById")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        cuentaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
