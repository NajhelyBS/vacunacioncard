package pe.edu.utp.vacunacioncard.model.comun;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * Clase Contacto embebible en entidades que requieren información de contacto.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contacto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "contacto_email")
    private String email;

    @Column(name = "contacto_telefono")
    private String telefono;

    @Column(name = "contacto_telefono_alt")
    private String telefonoAlternativo;
}