package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;

/**
 * Clase AplicacionDosis que representa la aplicación de una dosis específica a un paciente.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class AplicacionDosis {
    private final String id  = UUID.randomUUID().toString();
    private Paciente paciente;
    private Vacuna vacuna;
    private int dosisNumero;
    private final LocalDateTime fechaHora = LocalDateTime.now(ZoneId.of("America/Lima"));
    private Enfermero enfermero;
    private String loteVacuna;
    private String sitioInyeccion;
    private boolean exito = true;
    private String observaciones;

    public AplicacionDosis(Paciente paciente, Vacuna vacuna, int dosisNumero, Enfermero enfermero, String loteVacuna) {
    
        if (paciente == null || vacuna == null || enfermero == null) {
            throw new IllegalArgumentException("Datos obligatorios (Paciente, Vacuna, Enfermero) no pueden ser nulos o vacíos");
        }
        this.paciente = paciente;
        this.vacuna = vacuna;
        this.dosisNumero = dosisNumero;
        this.enfermero = enfermero;
        this.loteVacuna = loteVacuna;
    }
}
