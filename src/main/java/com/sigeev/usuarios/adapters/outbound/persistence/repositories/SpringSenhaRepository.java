package com.sigeev.usuarios.adapters.outbound.persistence.repositories;

import com.sigeev.usuarios.adapters.outbound.persistence.entities.SenhaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringSenhaRepository extends JpaRepository<SenhaEntity, UUID> {
    Optional<SenhaEntity> findByUsuarioId(UUID usuarioId);
}
