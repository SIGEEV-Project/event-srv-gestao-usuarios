package com.sigeev.usuarios.domain.ports.output;

import java.util.UUID;

public interface SenhaRepository {
    void salvarSenha(UUID usuarioId, String senhaHash);
    String buscarSenhaHash(UUID usuarioId);
}
