package pe.edu.utp.vacunacioncard.model.cita;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;

/**
 * Clase CitaVacunacion que representa una cita programada para la vacunación.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class CitaVacunacion {
    private String id;
    private Paciente paciente;
    private Vacuna vacuna;
    private LocalDateTime fechaHora;
    private String centroVacunacion;
    private String ubicacion;
    private String estado;
    private String observaciones;

    public CitaVacunacion(Paciente paciente, Vacuna vacuna, LocalDateTime fechaHora, String centroVacunacion) {
        this.id = java.util.UUID.randomUUID().toString();
        this.paciente = paciente;
        this.vacuna = vacuna;
        this.fechaHora = fechaHora;
        this.centroVacunacion = centroVacunacion;
        this.estado = "Programada";
    }
}
