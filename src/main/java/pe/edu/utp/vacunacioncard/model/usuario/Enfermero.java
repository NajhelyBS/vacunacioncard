package pe.edu.utp.vacunacioncard.model.usuario;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase Enfermero que representa un profesional de salud encargado de aplicar vacunas.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class Enfermero extends Usuario{
    private String colegiatura;
    private String especialidad;
    private String centroTrabajo;

    public Enfermero(String nombreCompleto, String dni, LocalDate fechaNacimiento, String colegiatura) {
        super(nombreCompleto, dni, fechaNacimiento);
        this.colegiatura = colegiatura;
    }
}
