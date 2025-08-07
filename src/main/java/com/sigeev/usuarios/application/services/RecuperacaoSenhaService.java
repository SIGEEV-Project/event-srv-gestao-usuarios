package com.sigeev.usuarios.application.services;

import com.sigeev.usuarios.domain.ports.input.RecuperacaoSenhaUseCase;
import com.sigeev.usuarios.domain.ports.output.UsuarioRepository;
import com.sigeev.usuarios.domain.ports.output.SenhaRepository;
import com.sigeev.usuarios.domain.entities.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecuperacaoSenhaService implements RecuperacaoSenhaUseCase {

    private final UsuarioRepository usuarioRepository;
    private final SenhaRepository senhaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void solicitarRecuperacaoSenha(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email não encontrado"));

        String token = UUID.randomUUID().toString();
        LocalDateTime expiracao = LocalDateTime.now().plusHours(1);

        usuario.setTokenRecuperacao(token);
        usuario.setTokenRecuperacaoExpiraEm(expiracao);
        usuarioRepository.save(usuario);

        // TODO: Enviar email com token de recuperação
    }

    @Override
    @Transactional(readOnly = true)
    public void validarTokenRecuperacao(String token) {
        Usuario usuario = usuarioRepository.findByTokenRecuperacao(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (usuario.getTokenRecuperacaoExpiraEm().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expirado");
        }
    }

    @Override
    @Transactional
    public void redefinirSenha(String token, String novaSenha) {
        Usuario usuario = usuarioRepository.findByTokenRecuperacao(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (usuario.getTokenRecuperacaoExpiraEm().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expirado");
        }

        senhaRepository.salvarSenha(usuario.getUsuarioId(), passwordEncoder.encode(novaSenha));
        
        usuario.setTokenRecuperacao(null);
        usuario.setTokenRecuperacaoExpiraEm(null);
        usuarioRepository.save(usuario);
    }
}
