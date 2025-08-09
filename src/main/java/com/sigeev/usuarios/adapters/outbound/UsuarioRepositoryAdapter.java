package com.sigeev.usuarios.adapters.outbound;

import com.sigeev.usuarios.adapters.outbound.persistence.UsuarioEntity;
import com.sigeev.usuarios.adapters.outbound.persistence.UsuarioJpaRepository;
import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.ports.UsuarioRepositoryPort;
import com.sigeev.usuarios.infrastructure.mappers.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = usuarioMapper.toEntity(usuario);
        UsuarioEntity savedEntity = usuarioRepository.save(entity);
        return usuarioMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf)
                .map(usuarioMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return usuarioRepository.existsByCpf(cpf);
    }

    @Override
    @Transactional
    public void updateSenha(UUID usuarioId, String novaSenhaHash) {
        findById(usuarioId).ifPresent(usuario -> {
            usuario.setSenha(novaSenhaHash);
            save(usuario);
        });
    }

    @Override
    @Transactional
    public void atualizarPerfil(UUID usuarioId, String novoPerfil) {
        usuarioRepository.atualizarPerfil(usuarioId, novoPerfil);
    }

    @Override
    @Transactional
    public void marcarExclusao(UUID usuarioId) {
        usuarioRepository.marcarExclusao(usuarioId, LocalDateTime.now());
    }

    @Override
    @Transactional
    public void registrarLogin(UUID usuarioId) {
        usuarioRepository.registrarLogin(usuarioId, LocalDateTime.now());
    }
}
