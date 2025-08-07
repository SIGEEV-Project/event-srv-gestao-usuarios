package com.sigeev.usuarios.adapters.outbound.persistence.repositories;

import com.sigeev.usuarios.adapters.outbound.persistence.entities.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringUsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
    Optional<UsuarioEntity> findByEmail(String email);
    Optional<UsuarioEntity> findByCpf(String cpf);
    Optional<UsuarioEntity> findByTokenRecuperacao(String token);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    Page<UsuarioEntity> findByPrimeiroNomeContainingIgnoreCaseOrUltimoNomeContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String primeiroNome, String ultimoNome, String email, Pageable pageable);
}
