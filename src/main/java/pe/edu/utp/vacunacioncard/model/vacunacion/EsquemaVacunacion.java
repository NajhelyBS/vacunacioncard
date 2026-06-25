package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;

/**
 * Clase EsquemaVacunacion que representa el esquema de vacunacion que debe seguir un paciente.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Builder
@Entity
@Table(name = "mae_esquema_vacunacion")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EsquemaVacunacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany
    @JoinTable(
        name = "mae_esquema_vacuna",
        joinColumns = @JoinColumn(name = "esquema_id"),
        inverseJoinColumns = @JoinColumn(name = "vacuna_id")
    )
    @Builder.Default
    private List<Vacuna> vacunasRequeridas = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mae_esquema_edades", joinColumns = @JoinColumn(name = "esquema_id"))
    @Column(name = "edad_recomendada")
    @Builder.Default
    private List<Integer> edadesRecomendadas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente pacienteAsignado;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "estado")
    @Builder.Default
    private String estado = "PENDIENTE";
}
