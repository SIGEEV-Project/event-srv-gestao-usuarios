package com.sigeev.usuarios.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Bean
    public OpenAPI openAPI() {
        Server localServer = new Server()
                .url("http://localhost:8080" + contextPath)
                .description("Servidor Local");

        Contact contact = new Contact()
                .name("Time SIGEEV")
                .email("contato@sigeev.com")
                .url("https://sigeev.com");

        Info info = new Info()
                .title("API de Gestão de Usuários - SIGEEV")
                .version("1.0.0")
                .description("API REST para gerenciamento de usuários do Sistema de Gestão de Eventos (SIGEEV)")
                .contact(contact)
                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"));

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}
