package pe.edu.utp.vacunacioncard.service.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.CartillaVacunacion;
import java.util.List;
import java.util.Optional;

public interface ICartillaVacunacionService {

    /** Obtiene todas las cartillas de vacunación registradas. */
    List<CartillaVacunacion> getAll();

    /** Busca una cartilla por su identificador único. */
    Optional<CartillaVacunacion> getById(Long id);

    /** Registra una nueva cartilla de vacunación. */
    CartillaVacunacion create(CartillaVacunacion cartilla);

    /** Actualiza los datos de una cartilla existente. */
    CartillaVacunacion update(CartillaVacunacion cartilla);

    /**
     * Desactiva lógicamente una cartilla (usa el flag "activa"), en lugar de
     * eliminarla físicamente, para preservar el historial médico del paciente.
     */
    void deactivate(Long id);

    /** Busca la cartilla asociada a un paciente. */
    Optional<CartillaVacunacion> findByPatient(Long pacienteId);

    /** Busca una cartilla mediante su código QR. */
    Optional<CartillaVacunacion> findByQrCode(String codigoQR);
}
