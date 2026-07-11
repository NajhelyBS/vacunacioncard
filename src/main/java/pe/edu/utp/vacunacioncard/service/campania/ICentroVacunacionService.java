package pe.edu.utp.vacunacioncard.service.campania;

import pe.edu.utp.vacunacioncard.model.campania.CentroVacunacion;
import java.util.List;
import java.util.Optional;

public interface ICentroVacunacionService {

    /** Obtiene todos los centros de vacunación registrados. */
    List<CentroVacunacion> getAll();

    /** Busca un centro de vacunación por su identificador único. */
    Optional<CentroVacunacion> getById(Long id);

    /** Registra un nuevo centro de vacunación. */
    CentroVacunacion create(CentroVacunacion centro);

    /** Actualiza los datos de un centro de vacunación existente. */
    CentroVacunacion update(CentroVacunacion centro);

    /** Elimina un centro de vacunación del sistema. */
    void deleteById(Long id);

    /** Lista los centros según su estado operativo (activo/inactivo). */
    List<CentroVacunacion> findByStatus(boolean activo);

    /** Busca centros de vacunación cuyo nombre contenga el texto indicado. */
    List<CentroVacunacion> searchByName(String nombre);
}