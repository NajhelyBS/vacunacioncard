package pe.edu.utp.vacunacioncard.model.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase Rol que representa un rol dentro del sistema.
 * Define los permisos y acceso de un usuario.
 *
 * @author Grupo 1
 * @version 1.0
 */


@Getter
@Setter
@NoArgsConstructor
public class Rol {
    private String id;
    private String nombre;
    private String descripcion;
    private List<Permiso> permisos;

    public Rol(String nombre, String descripcion) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.permisos = new ArrayList<>();
    }
}
