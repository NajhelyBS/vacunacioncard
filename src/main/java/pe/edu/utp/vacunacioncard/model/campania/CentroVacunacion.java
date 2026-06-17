package pe.edu.utp.vacunacioncard.model.campania;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.comun.Contacto;
import pe.edu.utp.vacunacioncard.model.comun.Direccion;
import java.util.UUID;
/**
 * Clase CentroVacunacion que representa un centro donde se aplican vacunas.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class CentroVacunacion {
    private final String id = UUID.randomUUID().toString();
    private String nombre;
    private Direccion direccion;
    private Contacto contacto;
    private int capacidadDiaria;
    private int personalDisponible;
    private String horarioAtencion;
    private boolean activo =  true;

    public CentroVacunacion(String nombre, Direccion direccion, int capacidadDiaria) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidadDiaria = capacidadDiaria;
    }
}
