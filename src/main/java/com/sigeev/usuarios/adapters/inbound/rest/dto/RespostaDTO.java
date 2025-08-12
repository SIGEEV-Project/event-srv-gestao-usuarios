package com.sigeev.usuarios.adapters.inbound.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespostaDTO {
    private boolean sucesso;
    private String mensagem;
    private Object dados;
    private List<ErroDTO> erros;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErroDTO {
        private String campo;
        private String mensagem;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsuarioCriadoDTO {
        private UUID usuarioId;
        private String nomeCompleto;
        private String email;
        private String tokenAcesso;
    }
}
