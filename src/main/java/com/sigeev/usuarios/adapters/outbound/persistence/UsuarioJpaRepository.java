package com.sigeev.usuarios.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, UUID> {
    Optional<UsuarioEntity> findByEmail(String email);
    Optional<UsuarioEntity> findByCpf(String cpf);
    Optional<UsuarioEntity> findByTokenRecuperacao(String token);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    @Modifying
    @Query("UPDATE UsuarioEntity u SET u.dataUltimoLogin = ?2 WHERE u.usuarioId = ?1")
    void registrarLogin(UUID usuarioId, LocalDateTime dataLogin);

    @Modifying
    @Query("UPDATE UsuarioEntity u SET u.perfil = ?2 WHERE u.usuarioId = ?1")
    void atualizarPerfil(UUID usuarioId, String novoPerfil);

    @Modifying
    @Query("UPDATE UsuarioEntity u SET u.status = 'inativo', u.dataExclusao = ?2 WHERE u.usuarioId = ?1")
    void marcarExclusao(UUID usuarioId, LocalDateTime dataExclusao);
}
