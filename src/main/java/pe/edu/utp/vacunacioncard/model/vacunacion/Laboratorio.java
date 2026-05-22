package pe.edu.utp.vacunacioncard.model.vacunacion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase Laboratorio que representa el laboratorio farmacéutico que produce una vacuna.
 * Contiene información básica del fabricante.
 *
 * @author Grupo 1
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor

public class Laboratorio {
    private String id;
    private String nombre;
    private String paisOrigen;

    public Laboratorio(String nombre, String paisOrigen) {
        this.id = java.util.UUID.randomUUID().toString();
        this.nombre = nombre;
        this.paisOrigen = paisOrigen;
    }
}
