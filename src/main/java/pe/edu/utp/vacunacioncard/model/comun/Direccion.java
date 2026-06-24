package pe.edu.utp.vacunacioncard.model.comun;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * Clase Direccion embebible en entidades que requieren dirección.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "dir_calle")
    private String calle;

    @Column(name = "dir_numero")
    private String numero;

    @Column(name = "dir_departamento")
    private String departamento;

    @Column(name = "dir_distrito")
    private String distrito;

    @Column(name = "dir_provincia")
    private String provincia;

    @Column(name = "dir_codigo_postal")
    private String codigoPostal;
}