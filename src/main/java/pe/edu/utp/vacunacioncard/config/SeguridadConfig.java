package pe.edu.utp.vacunacioncard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración de seguridad del sistema.
 * Expone el {@link PasswordEncoder} usado para codificar y verificar contraseñas.
 * <p>
 * Se emplea únicamente {@code spring-security-crypto} (BCrypt), sin activar la cadena
 * de filtros de Spring Security, por lo que no altera el acceso a los endpoints existentes.
 */
@Configuration
public class SeguridadConfig {

    /**
     * Proporciona el codificador de contraseñas basado en el algoritmo BCrypt.
     *
     * @return una instancia de {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
