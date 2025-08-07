package com.sigeev.usuarios.adapters.inbound.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CadastroUsuarioResponse {
    private boolean sucesso;
    private String mensagem;
    private DadosUsuario dados;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DadosUsuario {
        private UUID usuarioId;
        private String nomeCompleto;
        private String email;
        private String tokenAcesso;
    }
}
