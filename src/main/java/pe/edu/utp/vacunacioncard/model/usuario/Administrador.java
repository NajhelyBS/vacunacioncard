package pe.edu.utp.vacunacioncard.model.usuario;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase Administrador que representa un usuario administrador del sistema.
 * Gestiona configuraciones y usuarios.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class Administrador extends Usuario{
    private String nivelAcceso;
    private String area;

    
    public Administrador(String nombreCompleto, String dni, LocalDate fechaNacimiento) {
        super(nombreCompleto, dni, fechaNacimiento);
        this.nivelAcceso = "TOTAL";
    }
}
