package pe.edu.utp.vacunacioncard.dto.campania;

import pe.edu.utp.vacunacioncard.model.campania.CampaniaVacunacion;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;

import java.time.LocalDate;
import java.util.List;

/**
 * Datos de entrada para crear o actualizar una campaña de vacunación.
 * La vacuna se referencia por su identificador.
 */
public record CampaniaVacunacionRequest(
        String nombre,
        String descripcion,
        Long vacunaId,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        List<String> gruposObjetivo,
        int metaVacunacion,
        int vacunadosActuales,
        String estado) {

    /**
     * Construye una entidad {@link CampaniaVacunacion} a partir de los datos recibidos.
     *
     * @return la entidad lista para persistir.
     */
    public CampaniaVacunacion toEntity() {
        CampaniaVacunacion campania = new CampaniaVacunacion();
        campania.setNombre(nombre);
        campania.setDescripcion(descripcion);
        if (vacunaId != null) {
            Vacuna vacuna = new Vacuna();
            vacuna.setId(vacunaId);
            campania.setVacuna(vacuna);
        }
        campania.setFechaInicio(fechaInicio);
        campania.setFechaFin(fechaFin);
        if (gruposObjetivo != null) {
            campania.setGruposObjetivo(gruposObjetivo);
        }
        campania.setMetaVacunacion(metaVacunacion);
        campania.setVacunadosActuales(vacunadosActuales);
        if (estado != null) {
            campania.setEstado(estado);
        }
        return campania;
    }
}
