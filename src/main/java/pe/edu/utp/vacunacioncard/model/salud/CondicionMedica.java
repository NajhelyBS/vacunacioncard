package pe.edu.utp.vacunacioncard.model.salud;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase CondicionMedica que representa una condición médica del paciente.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class CondicionMedica {
    private String id;
    private String nombre;
    private String codigoCIE10;
    private String descripcion;
    private String tratamiento;
    private boolean activa;

    public CondicionMedica(String nombre, String codigoCIE10) {
        this.id = java.util.UUID.randomUUID().toString();
        this.nombre = nombre;
        this.codigoCIE10 = codigoCIE10;
        this.activa = true;
    }
}
