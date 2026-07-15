package pe.edu.utp.vacunacioncard.service.patron.factorymethod;

import pe.edu.utp.vacunacioncard.model.notificacion.Notificacion;
import pe.edu.utp.vacunacioncard.model.notificacion.NotificacionSistema;
import pe.edu.utp.vacunacioncard.model.usuario.Usuario;

/**
 * Creator concreto (ConcreteCreator) del patrón <b>Factory Method</b>.
 * <p>
 * Construye notificaciones de tipo {@link NotificacionSistema}, usadas para alertas y avisos
 * generales de la plataforma. El tipo se fija al instanciar la fábrica y determina qué
 * variante se produce; las notificaciones que crea nacen ya en estado {@code "ENVIADA"},
 * pues se despachan de inmediato.
 *
 * @see NotificacionCreator
 */
public class SistemaNotificacionCreator extends NotificacionCreator {

    private static final String ESTADO_ENVIADA = "ENVIADA";

    private final String tipo;

    /**
     * Crea la fábrica para un tipo de notificación de sistema.
     *
     * @param tipo Tipo de notificación a producir ({@code "SISTEMA"}, {@code "ALERTA"} o
     *             {@code "INFORMACION"}); se normaliza a mayúsculas.
     * @throws IllegalArgumentException Si el tipo es {@code null} o está en blanco.
     */
    public SistemaNotificacionCreator(String tipo) {
        this.tipo = validarTipo(tipo);
    }

    /**
     * Valida y normaliza el tipo recibido en el constructor.
     *
     * @param tipo Tipo de notificación a validar.
     * @return El tipo normalizado a mayúsculas.
     * @throws IllegalArgumentException Si el tipo es {@code null} o está en blanco.
     */
    private String validarTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de notificación no puede ser nulo o vacío");
        }
        return tipo.toUpperCase();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Produce una {@link NotificacionSistema} con el tipo configurado en esta fábrica.
     *
     * @throws IllegalArgumentException Si el tipo configurado no es uno de los soportados.
     */
    @Override
    protected Notificacion crearNotificacion(Usuario destinatario, String mensaje) {
        return switch (this.tipo) {
            case "SISTEMA", "ALERTA", "INFORMACION" -> new NotificacionSistema(destinatario, mensaje, this.tipo);
            default -> throw new IllegalArgumentException("Tipo de notificación de sistema no soportado: " + this.tipo);
        };
    }

    /**
     * {@inheritDoc}
     * <p>
     * Las alertas de sistema se despachan de inmediato, por lo que nacen como {@code "ENVIADA"}.
     */
    @Override
    protected String estadoInicial() {
        return ESTADO_ENVIADA;
    }
}
