package com.sanctuary.sih.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Serveur de développement");

        Contact contact = new Contact();
        contact.setName("Sanctuary Health");
        contact.setEmail("contact@sanctuary.med");

        Info info = new Info()
                .title("SIH - Système d'Information Hospitalier")
                .version("1.0.0")
                .description("API REST pour la gestion des processus médicaux au Cameroun")
                .contact(contact)
                .license(new License().name("Licence MIT").url("https://opensource.org/licenses/MIT"));

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}