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
        return toEntity(null);
    }

    /**
     * Construye una entidad {@link CondicionMedica} asignándole un identificador único.
     * Útil para operaciones de actualización (PUT).
     *
     * @param id el identificador único de la condición médica.
     * @return la entidad configurada con su ID correspondiente.
     */
    public CondicionMedica toEntity(Long id) {
        CondicionMedica condicion = new CondicionMedica();
        condicion.setId(id);
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
