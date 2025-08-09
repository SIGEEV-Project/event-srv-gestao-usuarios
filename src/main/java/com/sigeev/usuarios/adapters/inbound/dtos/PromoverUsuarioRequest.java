package com.sigeev.usuarios.adapters.inbound.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class PromoverUsuarioRequest {
    @NotNull(message = "O ID do administrador é obrigatório")
    private UUID adminId;

    @NotNull(message = "O novo perfil é obrigatório")
    private String novoPerfil;
}
