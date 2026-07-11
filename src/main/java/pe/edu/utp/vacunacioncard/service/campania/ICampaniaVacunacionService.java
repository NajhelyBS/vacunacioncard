package pe.edu.utp.vacunacioncard.service.campania;

import pe.edu.utp.vacunacioncard.model.campania.CampaniaVacunacion;
import java.util.List;
import java.util.Optional;

public interface ICampaniaVacunacionService {

    /** Obtiene todas las campañas de vacunación registradas. */
    List<CampaniaVacunacion> getAll();

    /** Busca una campaña por su identificador único. */
    Optional<CampaniaVacunacion> getById(Long id);

    /** Registra una nueva campaña de vacunación. */
    CampaniaVacunacion create(CampaniaVacunacion campania);

    /** Actualiza los datos de una campaña existente. */
    CampaniaVacunacion update(CampaniaVacunacion campania);

    /** Elimina una campaña de vacunación del sistema. */
    void deleteById(Long id);

    /** Lista las campañas según su estado operativo. */
    List<CampaniaVacunacion> findByStatus(String estado);
}
