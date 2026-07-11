package pe.edu.utp.vacunacioncard.dto.usuario;

import pe.edu.utp.vacunacioncard.model.usuario.Paciente;
import java.time.LocalDate;

/**
 * Vista pública de un paciente. Omite las colecciones internas de alergias,
 * condiciones médicas y contraindicaciones para evitar exponer la entidad JPA completa.
 *
 * @param id                 Identificador del paciente.
 * @param nombreCompleto     Nombre completo.
 * @param dni                Documento Nacional de Identidad.
 * @param fechaNacimiento    Fecha de nacimiento.
 * @param historiaClinicaId  Identificador de la historia clínica.
 * @param grupoSanguineo     Grupo sanguíneo del paciente.
 * @param activo             Estado activo/inactivo.
 */
public record PacienteResponse(
        Long id,
        String nombreCompleto,
        String dni,
        LocalDate fechaNacimiento,
        String historiaClinicaId,
        String grupoSanguineo,
        boolean activo) {

    public static PacienteResponse from(Paciente p) {
        return new PacienteResponse(
                p.getId(),
                p.getNombreCompleto(),
                p.getDni(),
                p.getFechaNacimiento(),
                p.getHistoriaClinicaId(),
                p.getGrupoSanguineo(),
                p.isActivo());
    }
}