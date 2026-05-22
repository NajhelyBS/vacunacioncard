package pe.edu.utp.vacunacioncard.model.vacunacion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase UbicacionAplicacion que representa la ubicación donde se aplica una vacuna.
 * Incluye información del lugar de atención.
 *
 * @author Grupo 1
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class UbicacionAplicacion {

    private String nombreLugar;
    private String direccion;
    private String distrito;

    public UbicacionAplicacion(String nombreLugar, String direccion, String distrito) {
        this.nombreLugar = nombreLugar;
        this.direccion = direccion;
        this.distrito = distrito;
    }
}