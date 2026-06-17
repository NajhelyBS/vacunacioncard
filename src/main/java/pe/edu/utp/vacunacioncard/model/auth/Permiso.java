package pe.edu.utp.vacunacioncard.model.auth;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase Permiso que representa un permiso asignado a un rol.
 * Define acciones permitidas dentro del sistema.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class Permiso {
    private final String id = UUID.randomUUID().toString();
    private String codigo;
    private String nombre;
    private String descripcion;

    public Permiso(String codigo, String nombre, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
