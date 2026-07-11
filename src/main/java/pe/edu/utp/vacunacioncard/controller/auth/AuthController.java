package pe.edu.utp.vacunacioncard.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.vacunacioncard.dto.auth.*;
import pe.edu.utp.vacunacioncard.model.auth.CuentaUsuario;
import pe.edu.utp.vacunacioncard.model.auth.SesionUsuario;
import pe.edu.utp.vacunacioncard.service.auth.ICuentaUsuarioService;
import pe.edu.utp.vacunacioncard.service.auth.ISesionUsuarioService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * Controlador REST para los flujos de autenticación: registro, inicio y cierre de sesión.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Registro, inicio y cierre de sesión")
@RequiredArgsConstructor
public class AuthController {

    private static final String ZONE_LIMA = "America/Lima";
    private static final int HORAS_VIGENCIA_SESION = 8;

    private final ICuentaUsuarioService cuentaService;
    private final ISesionUsuarioService sesionService;

    /**
     * Registra una nueva cuenta de usuario.
     *
     * @param request Datos de registro (username y contraseña).
     * @return La cuenta creada con estado HTTP 201.
     */
    @PostMapping("/register")
    @Operation(summary = "Registra una nueva cuenta de usuario", operationId = "register")
    public ResponseEntity<CuentaResponse> register(@RequestBody RegisterRequest request) {
        CuentaUsuario cuenta = CuentaUsuario.builder()
                .username(request.username())
                .passwordHash(request.password())
                .build();
        CuentaUsuario creada = cuentaService.create(cuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(CuentaResponse.from(creada));
    }

    /**
     * Autentica al usuario y, si las credenciales son válidas, crea una sesión con un token nuevo.
     *
     * @param request Credenciales de inicio de sesión.
     * @return El token y los datos básicos de la cuenta autenticada.
     */
    @PostMapping("/login")
    @Operation(summary = "Inicia sesión y devuelve un token de sesión", operationId = "login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        CuentaUsuario cuenta = cuentaService.authenticate(request.username(), request.password());

        String token = UUID.randomUUID().toString();
        SesionUsuario sesion = SesionUsuario.builder()
                .cuenta(cuenta)
                .token(token)
                .expiracion(LocalDateTime.now(ZoneId.of(ZONE_LIMA)).plusHours(HORAS_VIGENCIA_SESION))
                .activa(true)
                .build();
        sesionService.createSession(sesion);

        return ResponseEntity.ok(new LoginResponse(token, cuenta.getId(), cuenta.getUsername()));
    }

    /**
     * Cierra la sesión asociada al token recibido. Es idempotente: si el token no existe, no hace nada.
     *
     * @param request Token de la sesión a cerrar.
     * @return Respuesta sin contenido (HTTP 204).
     */
    @PostMapping("/logout")
    @Operation(summary = "Cierra la sesión asociada a un token", operationId = "logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest request) {
        sesionService.findByToken(request.token())
                .ifPresent(sesion -> sesionService.closeSession(sesion.getId()));
        return ResponseEntity.noContent().build();
    }
}
