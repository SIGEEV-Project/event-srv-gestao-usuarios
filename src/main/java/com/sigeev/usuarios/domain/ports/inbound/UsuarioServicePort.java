package com.sigeev.usuarios.domain.ports.inbound;

import com.sigeev.usuarios.domain.entities.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioServicePort {
    Usuario cadastrarUsuario(Usuario usuario);
    Optional<Usuario> buscarPorId(UUID id);
    Optional<Usuario> buscarPorEmail(String email);
    Optional<Usuario> buscarPorCpf(String cpf);
    void atualizarUsuario(Usuario usuario);
    void excluirUsuario(UUID id);
    void reativarUsuario(UUID id);
    void atualizarSenha(UUID id, String novaSenha);
    void solicitarRecuperacaoSenha(String email);
    void recuperarSenha(String token, String novaSenha);
}
