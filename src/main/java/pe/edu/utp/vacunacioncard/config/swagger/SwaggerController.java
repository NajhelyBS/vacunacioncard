package pe.edu.utp.vacunacioncard.config.swagger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * http://localhost:8001/swagger-ui
 */
@RestController
public class SwaggerController {

    @GetMapping("/swagger-ui")
    public RedirectView index() {
        return new RedirectView("swagger-ui/index.html");
    }
}