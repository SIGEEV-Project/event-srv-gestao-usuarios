package com.sigeev.usuarios.adapters.inbound.dtos;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ApiResponse {
    private boolean sucesso;
    private String mensagem;
    private Object dados;
    private List<ErroResponse> erros;

    @Data
    @Builder
    public static class ErroResponse {
        private String campo;
        private String mensagem;
    }
}
