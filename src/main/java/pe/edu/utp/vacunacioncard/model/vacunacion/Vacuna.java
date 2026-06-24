package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

/**
 * Clase Vacuna que representa una vacuna disponible en el sistema.
 * Contiene información médica y de administración.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Builder
@Entity
@Table(name = "mae_vacuna")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vacuna implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "laboratorio_id")
    private Laboratorio laboratorio;

    @Column(name = "dosis_requeridas")
    private int dosisRequeridas;

    @Column(name = "intervalo_dias")
    private int intervaloDias;

    @Column(name = "via_administracion")
    private String viaAdministracion;

    @Column(name = "temperatura_almacenamiento")
    private String temperaturaAlmacenamiento;

    @Column(name = "disponible")
    private boolean disponible = true;

    @ElementCollection
    @CollectionTable(name = "mae_vacuna_efectos", joinColumns = @JoinColumn(name = "vacuna_id"))
    @Column(name = "efecto")
    private List<String> efectosSecundarios = new ArrayList<>();

    @Column(name = "contraindicaciones")
    private String contraindicaciones;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
}