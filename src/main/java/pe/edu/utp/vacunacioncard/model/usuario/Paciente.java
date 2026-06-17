package pe.edu.utp.vacunacioncard.model.usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.utp.vacunacioncard.model.salud.Alergia;
import pe.edu.utp.vacunacioncard.model.salud.CondicionMedica;
import pe.edu.utp.vacunacioncard.model.salud.Contraindicacion;


/**
 * Clase Paciente que representa un paciente del sistema.
 * Contiene información médica y datos personales.
 *
 * @author Grupo 1
 * @version 1.0
 */



@Getter
@Setter
@NoArgsConstructor
public class Paciente extends Usuario{
    private String historiaClinicaId;
    private String grupoSanguineo;
    private List<Alergia> alergias = new ArrayList<>();
    private List<CondicionMedica> condicionesMedicas = new ArrayList<>();
    private List<Contraindicacion> contraindicaciones = new ArrayList<>();
    private SeguroMedico seguroMedico;
    private String contactoEmergencia;

    public Paciente(String nombreCompleto, String dni, LocalDate fechaNacimiento) {
        super(nombreCompleto, dni, fechaNacimiento);
    }
}
