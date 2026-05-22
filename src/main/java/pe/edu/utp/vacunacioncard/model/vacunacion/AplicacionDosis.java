package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.time.LocalDateTime;

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
    private String id;
    private Paciente paciente;
    private Vacuna vacuna;
    private int dosisNumero;
    private LocalDateTime fechaHora;
    private Enfermero enfermero;
    private String loteVacuna;
    private String sitioInyeccion;
    private boolean exito;
    private String observaciones;

    public AplicacionDosis(Paciente paciente, Vacuna vacuna, int dosisNumero, Enfermero enfermero, String loteVacuna) {
        this.id = java.util.UUID.randomUUID().toString();
        this.paciente = paciente;
        this.vacuna = vacuna;
        this.dosisNumero = dosisNumero;
        this.enfermero = enfermero;
        this.loteVacuna = loteVacuna;
        this.fechaHora = LocalDateTime.now();
        this.exito = true;
    }
}
