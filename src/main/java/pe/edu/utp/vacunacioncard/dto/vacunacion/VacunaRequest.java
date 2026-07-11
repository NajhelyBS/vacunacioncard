package pe.edu.utp.vacunacioncard.dto.vacunacion;

import pe.edu.utp.vacunacioncard.model.vacunacion.Laboratorio;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;

import java.time.LocalDate;
import java.util.List;

/**
 * Datos de entrada para registrar o actualizar una vacuna del catálogo.
 * El laboratorio se referencia por su identificador.
 */
public record VacunaRequest(
        String nombre,
        Long laboratorioId,
        int dosisRequeridas,
        int intervaloDias,
        String viaAdministracion,
        String temperaturaAlmacenamiento,
        Boolean disponible,
        List<String> efectosSecundarios,
        String contraindicaciones,
        LocalDate fechaVencimiento) {

    /**
     * Construye una entidad {@link Vacuna} a partir de los datos recibidos.
     *
     * @return la entidad lista para persistir.
     */
    public Vacuna toEntity() {
        Vacuna vacuna = new Vacuna();
        vacuna.setNombre(nombre);
        if (laboratorioId != null) {
            Laboratorio laboratorio = new Laboratorio();
            laboratorio.setId(laboratorioId);
            vacuna.setLaboratorio(laboratorio);
        }
        vacuna.setDosisRequeridas(dosisRequeridas);
        vacuna.setIntervaloDias(intervaloDias);
        vacuna.setViaAdministracion(viaAdministracion);
        vacuna.setTemperaturaAlmacenamiento(temperaturaAlmacenamiento);
        if (disponible != null) {
            vacuna.setDisponible(disponible);
        }
        if (efectosSecundarios != null) {
            vacuna.setEfectosSecundarios(efectosSecundarios);
        }
        vacuna.setContraindicaciones(contraindicaciones);
        vacuna.setFechaVencimiento(fechaVencimiento);
        return vacuna;
    }
}
