package pe.edu.utp.vacunacioncard.model.comun;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase Direccion que representa la dirección de una entidad o persona.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {
    private String calle;
    private String numero;
    private String departamento;
    private String distrito;
    private String provincia;
    private String codigoPostal;
}
