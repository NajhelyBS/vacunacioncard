package pe.edu.utp.vacunacioncard.dto.cita;


import pe.edu.utp.vacunacioncard.model.campania.CentroVacunacion;
import pe.edu.utp.vacunacioncard.model.cita.CitaVacunacion;
import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import pe.edu.utp.vacunacioncard.model.vacunacion.Vacuna;


import java.time.LocalDateTime;


/**
 * Datos de entrada para programar o modificar una cita de vacunación.
 * El paciente, la vacuna y el centro se referencian por su identificador.
 */
public record CitaVacunacionRequest(
        Long pacienteId,
        Long vacunaId,
        LocalDateTime fechaHora,
        Long centroVacunacionId,
        String estado,
        String observaciones) {


    public CitaVacunacion toEntity() {
        CitaVacunacion cita = new CitaVacunacion();
        if (pacienteId != null) {
            Paciente paciente = new Paciente();
            paciente.setId(pacienteId);
            cita.setPaciente(paciente);
        }
        if (vacunaId != null) {
            Vacuna vacuna = new Vacuna();
            vacuna.setId(vacunaId);
            cita.setVacuna(vacuna);
        }
        cita.setFechaHora(fechaHora);
        if (centroVacunacionId != null) {
            CentroVacunacion centro = new CentroVacunacion();
            centro.setId(centroVacunacionId);
            cita.setCentroVacunacion(centro);
        }
        if (estado != null) {
            cita.setEstado(estado);
        }
        cita.setObservaciones(observaciones);
        return cita;
    }
}
