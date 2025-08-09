package com.sigeev.usuarios.adapters.outbound.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID senhaId;

    @OneToOne
    @JoinColumn(name = "usuarioId", nullable = false)
    private UsuarioEntity usuario;

    @Column(nullable = false)
    private String senhaHash;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;
}
