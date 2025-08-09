package com.sigeev.usuarios;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@OpenAPIDefinition(
    info = @Info(
        title = "API de Gestão de Usuários",
        version = "1.0",
        description = "API para gerenciamento de usuários do sistema SIGEEV"
    )
)
public class GestaoUsuariosApplication {
    public static void main(String[] args) {
        SpringApplication.run(GestaoUsuariosApplication.class, args);
    }
}
