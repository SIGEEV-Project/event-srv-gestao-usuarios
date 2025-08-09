package com.sigeev.usuarios.adapters.inbound.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RenovarTokenRequest {
    @NotBlank(message = "O refresh token é obrigatório")
    private String refreshToken;
}
