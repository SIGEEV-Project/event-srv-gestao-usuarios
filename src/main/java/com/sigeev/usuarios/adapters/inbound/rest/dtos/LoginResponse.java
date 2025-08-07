package com.sigeev.usuarios.adapters.inbound.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private boolean sucesso;
    private String mensagem;
    private String tokenAcesso;
}
