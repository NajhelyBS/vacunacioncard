package pe.edu.utp.vacunacioncard.model.salud;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

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
    private final String id = UUID.randomUUID().toString();
    private String nombre;
    private String codigoCIE10;
    private String descripcion;
    private String tratamiento;
    private boolean activa =  true;

    public CondicionMedica(String nombre, String codigoCIE10) {
        if (nombre == null || codigoCIE10 == null) {
            throw new IllegalArgumentException("Nombre y código CIE10 son obligatorios");
        }
        this.nombre = nombre;
        this.codigoCIE10 = codigoCIE10;
    }
}
