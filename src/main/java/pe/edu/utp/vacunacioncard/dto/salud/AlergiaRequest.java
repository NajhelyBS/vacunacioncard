package pe.edu.utp.vacunacioncard.dto.salud;

import pe.edu.utp.vacunacioncard.model.salud.Alergia;

/**
 * Datos de entrada para registrar o actualizar una alergia.
 */
public record AlergiaRequest(
        String nombre,
        String tipo,
        String severidad,
        String sintomas,
        String tratamientoRecomendado) {

    public Alergia toEntity() {
        return toEntity(null);
    }
    /**
     * Construye una entidad {@link Alergia} a partir de los datos recibidos.
     *
     * @return la entidad lista para persistir.
     */
    public Alergia toEntity(Long id) {
        Alergia alergia = new Alergia();
        alergia.setId(id);
        alergia.setNombre(nombre);
        alergia.setTipo(tipo);
        alergia.setSeveridad(severidad);
        alergia.setSintomas(sintomas);
        alergia.setTratamientoRecomendado(tratamientoRecomendado);
        return alergia;
    }
}
