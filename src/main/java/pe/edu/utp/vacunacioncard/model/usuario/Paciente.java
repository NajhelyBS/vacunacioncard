package pe.edu.utp.vacunacioncard.model.usuario;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.salud.Alergia;
import pe.edu.utp.vacunacioncard.model.salud.CondicionMedica;
import pe.edu.utp.vacunacioncard.model.salud.Contraindicacion;

/**
 * Entidad Paciente que representa un paciente del sistema de vacunación.
 * Extiende de Usuario heredando los datos personales comunes.
 */
@Entity
@Table(name = "mae_paciente")
@NoArgsConstructor
@Getter
@Setter
public class Paciente extends Usuario {

    @Column(name = "historia_clinica_id")
    private String historiaClinicaId;

    @Column(name = "grupo_sanguineo")
    private String grupoSanguineo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private List<Alergia> alergias = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private List<CondicionMedica> condicionesMedicas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private List<Contraindicacion> contraindicaciones = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "seguro_medico_id")
    private SeguroMedico seguroMedico;

    @Column(name = "contacto_emergencia")
    private String contactoEmergencia;
}
