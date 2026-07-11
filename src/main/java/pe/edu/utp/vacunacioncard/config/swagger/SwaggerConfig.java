package pe.edu.utp.vacunacioncard.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * http://localhost:8001/swagger-ui/index.html
 */
@Configuration
public class SwaggerConfig {
    @Bean
    OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("VacunacionCard API")
                        .description("Sistema de Digitalización del Proceso de Vacunación")
                        .version("v1.0.0")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
