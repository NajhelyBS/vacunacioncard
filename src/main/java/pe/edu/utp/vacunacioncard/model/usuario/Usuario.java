package pe.edu.utp.vacunacioncard.model.usuario;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import pe.edu.utp.vacunacioncard.model.comun.Contacto;
import pe.edu.utp.vacunacioncard.model.comun.Direccion;

/**
 * Entidad base abstracta para los usuarios del sistema.
 * Implementa Serializable para cumplir con los estándares de persistencia y calidad.
 */
@Entity
@Table(name = "mae_usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Usuario implements Serializable {

    private static final long serialVersionUID = 1L; 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(name = "nombre_completo", nullable = false)
    protected String nombreCompleto;

    @Column(name = "dni", nullable = false, unique = true)
    protected String dni;

    @Column(name = "fecha_nacimiento", nullable = false)
    protected LocalDate fechaNacimiento;

    @Embedded 
    protected Contacto contacto;

    @Embedded
    protected Direccion direccion;

    @Column(name = "activo")
    protected boolean activo = true;

    protected Usuario(String nombreCompleto, String dni, LocalDate fechaNacimiento) {
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
    }
}