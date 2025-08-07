package com.sigeev.usuarios.domain.ports.input;

import com.sigeev.usuarios.domain.entities.Usuario;
import java.util.UUID;

public interface AtualizacaoUsuarioUseCase {
    Usuario atualizarDados(UUID usuarioId, Usuario dadosAtualizacao);
    Usuario atualizarSenha(UUID usuarioId, String senhaAtual, String novaSenha);
}
