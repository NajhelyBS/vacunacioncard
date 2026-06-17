package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
    private final String id = UUID.randomUUID().toString();
    private Paciente paciente;
    private String codigoQR;
    private List<RegistroVacuna> registrosVacunacion = new ArrayList<>();
    private List<EsquemaVacunacion> esquemasAsignados = new ArrayList<>();
    private boolean activa = true;
    private String estado = "ACTIVA";

    public CartillaVacunacion(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente es obligatorio para crear una cartilla de vacunación");
        }
        this.paciente = paciente;
    }
}
