package pe.edu.utp.vacunacioncard.model.salud;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String id;
    private String nombre;
    private String tipo;
    private String severidad;
    private String sintomas;
    private String tratamientoRecomendado;

    public Alergia(String nombre, String tipo, String severidad) {
        this.id = java.util.UUID.randomUUID().toString();
        this.nombre = nombre;
        this.tipo = tipo;
        this.severidad = severidad;
    }
}
