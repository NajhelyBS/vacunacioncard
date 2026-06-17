package pe.edu.utp.vacunacioncard.model.comun;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase Contacto que representa la información de contacto de una entidad o persona.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class Contacto {
    private String email;
    private String telefono;
    private String telefonoAlternativo;

    public Contacto(String email, String telefono){
        this.email = email;
        this.telefono = telefono;
    }
}
