package pe.edu.utp.vacunacioncard.dto.salud;

import pe.edu.utp.vacunacioncard.model.salud.Contraindicacion;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;

/**
 * Datos de entrada para registrar o actualizar una contraindicación.
 * La vacuna afectada se referencia por su identificador.
 */
public record ContraindicacionRequest(
        String descripcion,
        String severidad,
        String condicionAsociada,
        Long vacunaAfectadaId) {

    /**
     * Construye una entidad {@link Contraindicacion} a partir de los datos recibidos.
     *
     * @return la entidad lista para persistir.
     */
    public Contraindicacion toEntity() {
        Contraindicacion contraindicacion = new Contraindicacion();
        contraindicacion.setDescripcion(descripcion);
        contraindicacion.setSeveridad(severidad);
        contraindicacion.setCondicionAsociada(condicionAsociada);
        if (vacunaAfectadaId != null) {
            Vacuna vacuna = new Vacuna();
            vacuna.setId(vacunaAfectadaId);
            contraindicacion.setVacunaAfectada(vacuna);
        }
        return contraindicacion;
    }
}
