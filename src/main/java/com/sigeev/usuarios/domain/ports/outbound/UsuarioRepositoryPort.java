package com.sigeev.usuarios.domain.ports.outbound;

import com.sigeev.usuarios.domain.entities.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepositoryPort {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(UUID id);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByCpf(String cpf);
    void delete(UUID id);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}
