package pe.edu.utp.vacunacioncard.model.usuario;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.comun.Contacto;
import pe.edu.utp.vacunacioncard.model.comun.Direccion;

/**
 * Clase usuario que representa un usuario del sistema de vacunación.
 * Es la clase base para los diferentes tipos de usuarios.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public abstract class Usuario {
    protected String id;
    protected String nombreCompleto;
    protected String dni;
    protected LocalDate fechaNacimiento;
    protected Contacto contacto;
    protected Direccion direccion;
    protected boolean activo;

    public Usuario(String nombreCompleto, String dni, LocalDate fechaNacimiento){
        this.id = UUID.randomUUID().toString();
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.activo = true;
    }

}
