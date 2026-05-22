package pe.edu.utp.vacunacioncard.model.salud;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;

/**
 * Clase Contraindicacion que representa una contraindicación para la aplicación de vacunas.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class Contraindicacion {
    private String id;
    private String descripcion;
    private String severidad;
    private String condicionAsociada;
    private Vacuna vacunaAfectada;

    public Contraindicacion(String descripcion, String severidad) {
        this.id = java.util.UUID.randomUUID().toString();
        this.descripcion = descripcion;
        this.severidad = severidad;
    }
}
