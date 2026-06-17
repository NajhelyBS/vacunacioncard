package pe.edu.utp.vacunacioncard.model.cita;

import java.time.LocalDateTime;
import java.util.UUID;
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
    private final String id = UUID.randomUUID().toString();
    private Paciente paciente;
    private Vacuna vacuna;
    private LocalDateTime fechaHora;
    private String centroVacunacion;
    private String ubicacion;
    private String estado = "PROGRAMADA";
    private String observaciones;

    public CitaVacunacion(Paciente paciente, Vacuna vacuna, LocalDateTime fechaHora, String centroVacunacion) {
        if (paciente == null || vacuna == null || fechaHora == null) {
            throw new IllegalArgumentException("Los datos de la cita no pueden ser nulos");
        }
        
        this.paciente = paciente;
        this.vacuna = vacuna;
        this.fechaHora = fechaHora;
        this.centroVacunacion = centroVacunacion;
    }
}
