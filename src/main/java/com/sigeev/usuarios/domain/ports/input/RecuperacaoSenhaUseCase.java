package com.sigeev.usuarios.domain.ports.input;

import com.sigeev.usuarios.domain.entities.Usuario;

public interface RecuperacaoSenhaUseCase {
    void solicitarRecuperacaoSenha(String email);
    void validarTokenRecuperacao(String token);
    void redefinirSenha(String token, String novaSenha);
}
