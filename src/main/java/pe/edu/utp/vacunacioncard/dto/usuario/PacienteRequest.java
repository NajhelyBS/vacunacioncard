package pe.edu.utp.vacunacioncard.dto.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import pe.edu.utp.vacunacioncard.model.usuario.SeguroMedico;

import java.time.LocalDate;

/**
 * Datos de entrada para registrar o actualizar un paciente.
 * El seguro médico se referencia por su identificador; las colecciones clínicas se gestionan aparte.
 */
public record PacienteRequest(
        String nombreCompleto,
        String dni,
        LocalDate fechaNacimiento,
        String historiaClinicaId,
        String grupoSanguineo,
        Long seguroMedicoId,
        String contactoEmergencia,
        Boolean activo) {

    /**
     * Construye una entidad {@link Paciente} a partir de los datos recibidos.
     *
     * @return la entidad lista para persistir.
     */
    public Paciente toEntity() {
        Paciente paciente = new Paciente();
        paciente.setNombreCompleto(nombreCompleto);
        paciente.setDni(dni);
        paciente.setFechaNacimiento(fechaNacimiento);
        paciente.setHistoriaClinicaId(historiaClinicaId);
        paciente.setGrupoSanguineo(grupoSanguineo);
        if (seguroMedicoId != null) {
            SeguroMedico seguro = new SeguroMedico();
            seguro.setId(seguroMedicoId);
            paciente.setSeguroMedico(seguro);
        }
        paciente.setContactoEmergencia(contactoEmergencia);
        if (activo != null) {
            paciente.setActivo(activo);
        }
        return paciente;
    }
}
