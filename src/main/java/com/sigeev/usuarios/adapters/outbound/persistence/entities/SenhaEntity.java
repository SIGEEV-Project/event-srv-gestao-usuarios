package com.sigeev.usuarios.adapters.outbound.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Senhas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SenhaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID senhaId;

    @Column(nullable = false)
    private UUID usuarioId;

    @Column(nullable = false)
    private String senhaHash;

    @Column
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioId", insertable = false, updatable = false)
    private UsuarioEntity usuario;
}
