package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import java.util.UUID;
import java.time.ZoneId;

/**
 * Clase RegistroVacuna que representa el registro de aplicación de una vacuna.
 * Contiene información de la dosis aplicada.
 *
 * @author Grupo 1
 * @version 1.0
 */


@Getter
@Setter
@NoArgsConstructor
public class RegistroVacuna {
    private final String id = UUID.randomUUID().toString();
    private Vacuna vacuna;
    private int numeroDosis;
    private LocalDateTime fechaAplicacion = LocalDateTime.now(ZoneId.of("America/Lima"));
    private Enfermero enfermeroAplicador;
    private String lote;
    private UbicacionAplicacion lugarAplicacion;
    private String estado = "APLICADA";
    private String reaccionesAdversas;
    private LocalDateTime proximaDosis;

    public RegistroVacuna(Vacuna vacuna, int numeroDosis, Enfermero enfermeroAplicador, String lote) {
        if (vacuna == null || enfermeroAplicador == null || lote == null || lote.isBlank()) {
            throw new IllegalArgumentException("Datos obligatorios (Vacuna, Enfermero Aplicador, Lote) no pueden ser nulos o vacíos");
        }
        
        this.vacuna = vacuna;
        this.numeroDosis = numeroDosis;
        this.enfermeroAplicador = enfermeroAplicador;
        this.lote = lote;
    }
}
