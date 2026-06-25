package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;

/**
 * Clase AplicacionDosis que representa la aplicacion de una dosis especifica a un paciente.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Builder
@Entity
@Table(name = "mae_aplicacion_dosis")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AplicacionDosis implements Serializable {

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

    @Column(name = "dosis_numero")
    private int dosisNumero;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "enfermero_id", nullable = false)
    private Enfermero enfermero;

    @Column(name = "lote_vacuna")
    private String loteVacuna;

    @Column(name = "sitio_inyeccion")
    private String sitioInyeccion;

    @Column(name = "exito")
    @Builder.Default
    private boolean exito = true;

    @Column(name = "observaciones")
    private String observaciones;
}
