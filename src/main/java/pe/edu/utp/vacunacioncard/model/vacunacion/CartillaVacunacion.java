package pe.edu.utp.vacunacioncard.model.vacunacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;

/**
 * Clase CartillaVacunacion que representa la cartilla de vacunacion de un paciente.
 * Almacena el historial de vacunas aplicadas.
 *
 * @author Grupo 1
 * @version 1.0
 */

@Builder
@Entity
@Table(name = "mae_cartilla_vacunacion")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartillaVacunacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(name = "codigo_qr")
    private String codigoQR;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cartilla_id")
    @Builder.Default
    private List<RegistroVacuna> registrosVacunacion = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cartilla_id")
    @Builder.Default
    private List<EsquemaVacunacion> esquemasAsignados = new ArrayList<>();

    @Builder.Default
    @Column(name = "activa")
    private boolean activa = true;

    @Column(name = "estado")
    @Builder.Default
    private String estado = "ACTIVA";
}
