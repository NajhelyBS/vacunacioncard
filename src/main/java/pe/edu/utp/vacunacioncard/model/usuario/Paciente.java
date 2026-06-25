package pe.edu.utp.vacunacioncard.model.usuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.comun.Contacto;
import pe.edu.utp.vacunacioncard.model.comun.Direccion;
import pe.edu.utp.vacunacioncard.model.salud.Alergia;
import pe.edu.utp.vacunacioncard.model.salud.CondicionMedica;
import pe.edu.utp.vacunacioncard.model.salud.Contraindicacion;

/**
 * Clase Paciente que representa un paciente del sistema.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Builder
@Entity
@Table(name = "mae_paciente")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @Column(name = "dni", nullable = false, unique = true)
    private String dni;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Embedded
    private Contacto contacto;

    @Embedded
    private Direccion direccion;

    @Builder.Default
    @Column(name = "activo")
    private boolean activo = true;

    @Column(name = "historia_clinica_id")
    private String historiaClinicaId;

    @Column(name = "grupo_sanguineo")
    private String grupoSanguineo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    @Builder.Default
    private List<Alergia> alergias = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    @Builder.Default
    private List<CondicionMedica> condicionesMedicas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    @Builder.Default
    private List<Contraindicacion> contraindicaciones = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "seguro_medico_id")
    private SeguroMedico seguroMedico;

    @Column(name = "contacto_emergencia")
    private String contactoEmergencia;
}