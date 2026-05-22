package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;

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
    private String id;
    private Vacuna vacuna;
    private int numeroDosis;
    private LocalDateTime fechaAplicacion;
    private Enfermero enfermeroAplicador;
    private String lote;
    private UbicacionAplicacion lugarAplicacion;
    private String estado;
    private String reaccionesAdversas;
    private LocalDateTime proximaDosis;

    public RegistroVacuna(Vacuna vacuna, int numeroDosis, Enfermero enfermeroAplicador, String lote) {
        this.id = java.util.UUID.randomUUID().toString();
        this.vacuna = vacuna;
        this.numeroDosis = numeroDosis;
        this.enfermeroAplicador = enfermeroAplicador;
        this.lote = lote;
        this.fechaAplicacion = LocalDateTime.now();
        this.estado = "APLICADA";
    }
}
