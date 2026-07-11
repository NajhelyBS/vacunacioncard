package pe.edu.utp.vacunacioncard.controller.auth;

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
@RequiredArgsConstructor
public class CuentaUsuarioController {

    private final ICuentaUsuarioService cuentaService;

    /**
     * Lista todas las cuentas de usuario registradas.
     *
     * @return Lista de cuentas en formato público.
     */
    @GetMapping
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
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        cuentaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
