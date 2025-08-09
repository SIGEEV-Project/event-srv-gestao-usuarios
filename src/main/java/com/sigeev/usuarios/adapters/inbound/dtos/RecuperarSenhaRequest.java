package com.sigeev.usuarios.adapters.inbound.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RecuperarSenhaRequest {
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
}
