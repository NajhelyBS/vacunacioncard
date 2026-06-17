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
    private final String id = UUID.randomUUID().toString();
    protected String nombreCompleto;
    protected String dni;
    protected LocalDate fechaNacimiento;
    protected Contacto contacto;
    protected Direccion direccion;
    protected boolean activo =  true;

    protected Usuario(String nombreCompleto, String dni, LocalDate fechaNacimiento){
        if (nombreCompleto == null || nombreCompleto.isBlank() || 
            dni == null || dni.isBlank() || 
            fechaNacimiento == null) {
            throw new IllegalArgumentException("Datos obligatorios (Nombre, DNI y Fecha de Nacimiento) no pueden ser nulos");
        }
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
    }

}
