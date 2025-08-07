package com.sigeev.usuarios.adapters.outbound.persistence;

import com.sigeev.usuarios.adapters.outbound.persistence.entities.UsuarioEntity;
import com.sigeev.usuarios.adapters.outbound.persistence.repositories.SpringUsuarioRepository;
import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.ports.output.UsuarioRepository;
import com.sigeev.usuarios.infrastructure.mappers.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepository {
    
    private final SpringUsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = usuarioMapper.toEntity(usuario);
        entity = usuarioRepository.save(entity);
        return usuarioMapper.toDomain(entity);
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
    public Page<Usuario> buscarPorFiltro(String filtro, Pageable paginacao) {
        return usuarioRepository.findByPrimeiroNomeContainingIgnoreCaseOrUltimoNomeContainingIgnoreCaseOrEmailContainingIgnoreCase(
                filtro, filtro, filtro, paginacao)
                .map(usuarioMapper::toDomain);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return usuarioRepository.existsByCpf(cpf);
    }

    @Override
    public Optional<Usuario> findByTokenRecuperacao(String token) {
        return usuarioRepository.findByTokenRecuperacao(token)
                .map(usuarioMapper::toDomain);
    }
}
