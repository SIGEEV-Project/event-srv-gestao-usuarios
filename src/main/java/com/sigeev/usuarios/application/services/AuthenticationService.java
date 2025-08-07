package com.sigeev.usuarios.application.services;

import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.ports.input.AuthenticationUseCase;
import com.sigeev.usuarios.domain.ports.output.SenhaRepository;
import com.sigeev.usuarios.domain.ports.output.UsuarioRepository;
import com.sigeev.usuarios.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationUseCase {

    private final UsuarioRepository usuarioRepository;
    private final SenhaRepository senhaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional(readOnly = true)
    public String authenticate(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Email ou senha inválidos"));

        if (!isPasswordValid(senha, usuario)) {
            throw new BadCredentialsException("Email ou senha inválidos");
        }

        return jwtService.generateToken(usuario.getUsuarioId(), usuario.getEmail(), usuario.getPerfil().toString());
    }

    private boolean isPasswordValid(String rawPassword, Usuario usuario) {
        String senhaHash = senhaRepository.buscarSenhaHash(usuario.getUsuarioId());
        if (senhaHash == null) {
            return false;
        }
        return passwordEncoder.matches(rawPassword, senhaHash);
    }
}
