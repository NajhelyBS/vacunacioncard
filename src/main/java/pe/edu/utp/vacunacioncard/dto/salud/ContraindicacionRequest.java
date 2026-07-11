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
        return toEntity(null);
    }

    /**
     * Construye una entidad {@link Contraindicacion} asignándole un identificador único.
     * Útil para operaciones de actualización (PUT).
     *
     * @param id el identificador único de la contraindicación.
     * @return la entidad configurada con su ID correspondiente.
     */
    public Contraindicacion toEntity(Long id) {
        Contraindicacion contraindicacion = new Contraindicacion();
        contraindicacion.setId(id);
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
