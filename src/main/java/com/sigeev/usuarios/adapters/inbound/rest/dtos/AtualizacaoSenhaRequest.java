package com.sigeev.usuarios.adapters.inbound.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtualizacaoSenhaRequest {
    @NotBlank(message = "A senha atual é obrigatória")
    private String senhaAtual;

    @NotBlank(message = "A nova senha é obrigatória")
    private String novaSenha;
}
