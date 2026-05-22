package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;

/**
 * Clase CartillaVacunacion que representa la cartilla de vacunación de un paciente.
 * Almacena el historial de vacunas aplicadas.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class CartillaVacunacion {
    private String id;
    private Paciente paciente;
    private String codigoQR;
    private List<RegistroVacuna> registrosVacunacion;
    private List<EsquemaVacunacion> esquemasAsignados;
    private boolean activa;
    private String estado;

    public CartillaVacunacion(Paciente paciente) {
        this.id = java.util.UUID.randomUUID().toString();
        this.paciente = paciente;
        this.registrosVacunacion = new ArrayList<>();
        this.esquemasAsignados = new ArrayList<>();
        this.activa = true;
        this.estado = "ACTIVA";
    }
}
