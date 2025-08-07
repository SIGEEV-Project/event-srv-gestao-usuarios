package com.sigeev.usuarios.adapters.outbound.persistence;

import com.sigeev.usuarios.adapters.outbound.persistence.entities.SenhaEntity;
import com.sigeev.usuarios.adapters.outbound.persistence.repositories.SpringSenhaRepository;
import com.sigeev.usuarios.domain.ports.output.SenhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SenhaRepositoryAdapter implements SenhaRepository {

    private final SpringSenhaRepository senhaRepository;

    @Override
    public void salvarSenha(UUID usuarioId, String senhaHash) {
        SenhaEntity senhaEntity = SenhaEntity.builder()
                .usuarioId(usuarioId)
                .senhaHash(senhaHash)
                .dataCriacao(LocalDateTime.now())
                .build();
        senhaRepository.save(senhaEntity);
    }

    @Override
    public String buscarSenhaHash(UUID usuarioId) {
        return senhaRepository.findByUsuarioId(usuarioId)
                .map(SenhaEntity::getSenhaHash)
                .orElse(null);
    }
}
