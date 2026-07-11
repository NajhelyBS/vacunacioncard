package pe.edu.utp.vacunacioncard.dto.salud;

import pe.edu.utp.vacunacioncard.model.salud.CondicionMedica;

/**
 * Datos de entrada para registrar o actualizar una condición médica.
 * Expone únicamente los campos que el cliente puede definir (no incluye el id).
 */
public record CondicionMedicaRequest(
        String nombre,
        String codigoCIE10,
        String descripcion,
        String tratamiento,
        Boolean activa) {

    /**
     * Construye una entidad {@link CondicionMedica} a partir de los datos recibidos.
     *
     * @return la entidad lista para persistir.
     */
    public CondicionMedica toEntity() {
        CondicionMedica condicion = new CondicionMedica();
        condicion.setNombre(nombre);
        condicion.setCodigoCIE10(codigoCIE10);
        condicion.setDescripcion(descripcion);
        condicion.setTratamiento(tratamiento);
        if (activa != null) {
            condicion.setActiva(activa);
        }
        return condicion;
    }
}
