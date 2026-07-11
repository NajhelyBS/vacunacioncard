package pe.edu.utp.vacunacioncard.dto.vacunacion;

import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import pe.edu.utp.vacunacioncard.model.vacunacion.EsquemaVacunacion;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Datos de entrada para crear o actualizar un esquema de vacunación.
 * Las vacunas requeridas y el paciente asignado se referencian por sus identificadores.
 */
public record EsquemaVacunacionRequest(
        String nombre,
        String descripcion,
        List<Long> vacunasRequeridasIds,
        List<Integer> edadesRecomendadas,
        Long pacienteAsignadoId,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        String estado) {

    /**
     * Construye una entidad {@link EsquemaVacunacion} a partir de los datos recibidos.
     *
     * @return la entidad lista para persistir.
     */
    public EsquemaVacunacion toEntity() {
        EsquemaVacunacion esquema = new EsquemaVacunacion();
        esquema.setNombre(nombre);
        esquema.setDescripcion(descripcion);
        if (vacunasRequeridasIds != null) {
            List<Vacuna> vacunas = new ArrayList<>();
            for (Long vacunaId : vacunasRequeridasIds) {
                Vacuna vacuna = new Vacuna();
                vacuna.setId(vacunaId);
                vacunas.add(vacuna);
            }
            esquema.setVacunasRequeridas(vacunas);
        }
        if (edadesRecomendadas != null) {
            esquema.setEdadesRecomendadas(edadesRecomendadas);
        }
        if (pacienteAsignadoId != null) {
            Paciente paciente = new Paciente();
            paciente.setId(pacienteAsignadoId);
            esquema.setPacienteAsignado(paciente);
        }
        esquema.setFechaInicio(fechaInicio);
        esquema.setFechaFin(fechaFin);
        if (estado != null) {
            esquema.setEstado(estado);
        }
        return esquema;
    }
}
