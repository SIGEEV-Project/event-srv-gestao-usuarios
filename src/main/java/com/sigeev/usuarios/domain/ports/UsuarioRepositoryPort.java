package com.sigeev.usuarios.domain.ports;

import com.sigeev.usuarios.domain.entities.Usuario;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepositoryPort {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(UUID id);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    void updateSenha(UUID usuarioId, String novaSenhaHash);
    void atualizarPerfil(UUID usuarioId, String novoPerfil);
    void marcarExclusao(UUID usuarioId);
    void registrarLogin(UUID usuarioId);
}
