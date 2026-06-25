package pe.edu.utp.vacunacioncard.model.cita;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;

/**
 * Entidad CitaVacunacion que representa una cita programada para vacunación.
 *
 */

@Builder
@Entity
@Table(name = "mae_cita_vacunacion")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CitaVacunacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "vacuna_id", nullable = false)
    private Vacuna vacuna;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "centro_vacunacion")
    private String centroVacunacion;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "estado")
    @Builder.Default
    private String estado = "PROGRAMADA";

    @Column(name = "observaciones")
    private String observaciones;
}