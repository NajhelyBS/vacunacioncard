package pe.edu.utp.vacunacioncard.dto.salud;

import pe.edu.utp.vacunacioncard.model.salud.Alergia;

/**
 * Datos de entrada para registrar o actualizar una alergia.
 * Expone únicamente los campos que el cliente puede definir (no incluye el id ni estados internos).
 */
public record AlergiaRequest(
        String nombre,
        String tipo,
        String severidad,
        String sintomas,
        String tratamientoRecomendado) {

    /**
     * Construye una entidad {@link Alergia} a partir de los datos recibidos.
     *
     * @return la entidad lista para persistir.
     */
    public Alergia toEntity() {
        Alergia alergia = new Alergia();
        alergia.setNombre(nombre);
        alergia.setTipo(tipo);
        alergia.setSeveridad(severidad);
        alergia.setSintomas(sintomas);
        alergia.setTratamientoRecomendado(tratamientoRecomendado);
        return alergia;
    }
}
