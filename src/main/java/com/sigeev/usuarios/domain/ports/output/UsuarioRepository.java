package com.sigeev.usuarios.domain.ports.output;

import com.sigeev.usuarios.domain.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(UUID id);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    Optional<Usuario> findByTokenRecuperacao(String token);
    Page<Usuario> buscarPorFiltro(String filtro, Pageable paginacao);
}
