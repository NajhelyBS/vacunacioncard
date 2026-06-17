package pe.edu.utp.vacunacioncard.model.vacunacion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

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
    private final String id = UUID.randomUUID().toString();
    private String nombre;
    private String paisOrigen;

    public Laboratorio(String nombre, String paisOrigen) {
        if (nombre == null || nombre.isBlank() || paisOrigen == null || paisOrigen.isBlank()) {
            throw new IllegalArgumentException("Datos obligatorios (Nombre y País de Origen) no pueden ser nulos o vacíos");
        }
        this.nombre = nombre;
        this.paisOrigen = paisOrigen;
    }
}
