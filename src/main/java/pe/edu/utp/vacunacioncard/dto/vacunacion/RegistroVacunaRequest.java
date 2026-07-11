package pe.edu.utp.vacunacioncard.dto.vacunacion;

import pe.edu.utp.vacunacioncard.model.usuario.Enfermero;
import pe.edu.utp.vacunacioncard.model.vacunacion.RegistroVacuna;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;

import java.time.LocalDateTime;

/**
 * Datos de entrada para registrar o actualizar la aplicación de una dosis.
 * La vacuna y el enfermero aplicador se referencian por su identificador.
 */
public record RegistroVacunaRequest(
        Long vacunaId,
        int numeroDosis,
        LocalDateTime fechaAplicacion,
        Long enfermeroAplicadorId,
        String lote,
        String estado,
        String reaccionesAdversas,
        LocalDateTime proximaDosis) {

    /**
     * Construye una entidad {@link RegistroVacuna} a partir de los datos recibidos.
     *
     * @return la entidad lista para persistir.
     */
    public RegistroVacuna toEntity() {
        RegistroVacuna registro = new RegistroVacuna();
        if (vacunaId != null) {
            Vacuna vacuna = new Vacuna();
            vacuna.setId(vacunaId);
            registro.setVacuna(vacuna);
        }
        registro.setNumeroDosis(numeroDosis);
        registro.setFechaAplicacion(fechaAplicacion);
        if (enfermeroAplicadorId != null) {
            Enfermero enfermero = new Enfermero();
            enfermero.setId(enfermeroAplicadorId);
            registro.setEnfermeroAplicador(enfermero);
        }
        registro.setLote(lote);
        if (estado != null) {
            registro.setEstado(estado);
        }
        registro.setReaccionesAdversas(reaccionesAdversas);
        registro.setProximaDosis(proximaDosis);
        return registro;
    }
}
