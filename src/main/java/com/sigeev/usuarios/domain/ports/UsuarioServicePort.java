package com.sigeev.usuarios.domain.ports;

import com.sigeev.usuarios.domain.entities.Usuario;
import java.util.UUID;

public interface UsuarioServicePort {
    Usuario cadastrarUsuario(Usuario usuario, String senha);
    String autenticarUsuario(String email, String senha);
    Usuario atualizarPerfil(UUID usuarioId, Usuario usuario);
    void promoverUsuario(UUID usuarioId, UUID adminId, String novoPerfil);
    void rebaixarUsuario(UUID usuarioId, UUID adminId);
    void excluirConta(UUID usuarioId);
    void trocarSenha(UUID usuarioId, String senhaAtual, String novaSenha);
    void solicitarRecuperacaoSenha(String email);
    void redefinirSenha(String token, String novaSenha);
    String renovarToken(String refreshToken);
}
