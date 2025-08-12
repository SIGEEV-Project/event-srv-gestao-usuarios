package com.sigeev.usuarios.adapters.outbound.persistence.adapter;

import com.sigeev.usuarios.adapters.outbound.persistence.repository.UsuarioJpaRepository;
import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.ports.outbound.UsuarioRepositoryPort;
import com.sigeev.usuarios.infrastructure.mappers.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {
    
    private final UsuarioJpaRepository repository;
    private final UsuarioMapper mapper;

    @Override
    public Usuario save(Usuario usuario) {
        var entity = mapper.toEntity(usuario);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByCpf(String cpf) {
        return repository.findByCpf(cpf)
                .map(mapper::toDomain);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return repository.existsByCpf(cpf);
    }
}
