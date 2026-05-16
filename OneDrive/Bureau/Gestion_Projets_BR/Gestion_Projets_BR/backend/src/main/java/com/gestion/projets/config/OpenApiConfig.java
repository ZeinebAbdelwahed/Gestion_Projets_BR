package com.gestion.projets.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestion des Projets — API REST")
                        .version("1.0.0")
                        .description("API complète de gestion des projets avec budgets, ressources et suivi financier. " +
                                "Sujet 7 — Spring Boot 3.2.5 / Angular 17")
                        .contact(new Contact()
                                .name("Gestion Projets BR")
                                .email("contact@gestion-projets.com"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Serveur local"),
                        new Server().url("http://backend:8080").description("Serveur Docker")
                ));
    }
}
