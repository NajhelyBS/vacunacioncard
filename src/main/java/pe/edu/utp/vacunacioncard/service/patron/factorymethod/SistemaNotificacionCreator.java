package pe.edu.utp.vacunacioncard.service.patron.factorymethod;

import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionSistema;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

/** ConcreteCreator: construye notificaciones de sistema/alerta. */
public class SistemaNotificacionCreator extends NotificacionCreator {

    private static final String ESTADO_ENVIADA = "ENVIADA";
    private final String tipo;

    public SistemaNotificacionCreator(String tipo) {
        this.tipo = validarTipo(tipo);
    }

    private String validarTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de notificación no puede ser nulo o vacío");
        }
        return tipo.toUpperCase();
    }

    @Override
    protected Notificacion crearNotificacion(Usuario destinatario, String mensaje) {
        return switch (this.tipo) {
            case "SISTEMA", "ALERTA", "INFORMACION" -> new NotificacionSistema(destinatario, mensaje, this.tipo);
            default -> throw new IllegalArgumentException("Tipo de notificación de sistema no soportado: " + this.tipo);
        };
    }

    @Override
    protected String estadoInicial() {
        return ESTADO_ENVIADA;
    }
}