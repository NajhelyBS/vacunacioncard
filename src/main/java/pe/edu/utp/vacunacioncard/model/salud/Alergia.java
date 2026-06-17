package pe.edu.utp.vacunacioncard.model.salud;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
/**
 * Clase alergia que representa una alergia del paciente.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class Alergia {
    private final String id = UUID.randomUUID().toString();
    private String nombre;
    private String tipo;
    private String severidad;
    private String sintomas;
    private String tratamientoRecomendado;

    public Alergia(String nombre, String tipo, String severidad) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la alergia no puede ser nulo o vacío");
        }
        this.nombre = nombre;
        this.tipo = tipo;
        this.severidad = severidad;
    }
}
