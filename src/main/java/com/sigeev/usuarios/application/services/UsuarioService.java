package com.sigeev.usuarios.application.services;

import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.exceptions.NegocioException;
import com.sigeev.usuarios.domain.ports.UsuarioRepositoryPort;
import com.sigeev.usuarios.domain.ports.UsuarioServicePort;
import com.sigeev.usuarios.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioServicePort {
    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public Usuario cadastrarUsuario(Usuario usuario, String senha) {
        validarNovoUsuario(usuario);
        usuario.setSenha(passwordEncoder.encode(senha));
        return usuarioRepository.save(usuario);
    }

    @Override
    public String autenticarUsuario(String email, String senha) {
        return usuarioRepository.findByEmail(email)
                .filter(usuario -> passwordEncoder.matches(senha, usuario.getSenha()))
                .map(usuario -> {
                    usuarioRepository.registrarLogin(usuario.getUsuarioId());
                    return jwtService.gerarToken(usuario);
                })
                .orElseThrow(() -> new NegocioException("Credenciais inválidas"));
    }

    @Override
    @Transactional
    public Usuario atualizarPerfil(UUID usuarioId, Usuario dadosAtualizacao) {
        return usuarioRepository.findById(usuarioId)
                .map(usuario -> {
                    atualizarDadosUsuario(usuario, dadosAtualizacao);
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new NegocioException("Usuário não encontrado"));
    }

    @Override
    @Transactional
    public void promoverUsuario(UUID usuarioId, UUID adminId, String novoPerfil) {
        validarAdmin(adminId);
        usuarioRepository.findById(usuarioId)
                .ifPresentOrElse(
                        usuario -> usuarioRepository.atualizarPerfil(usuarioId, novoPerfil),
                        () -> { throw new NegocioException("Usuário não encontrado"); }
                );
    }

    @Override
    @Transactional
    public void rebaixarUsuario(UUID usuarioId, UUID adminId) {
        validarAdmin(adminId);
        usuarioRepository.findById(usuarioId)
                .ifPresentOrElse(
                        usuario -> usuarioRepository.atualizarPerfil(usuarioId, "PARTICIPANTE"),
                        () -> { throw new NegocioException("Usuário não encontrado"); }
                );
    }

    @Override
    @Transactional
    public void excluirConta(UUID usuarioId) {
        usuarioRepository.findById(usuarioId)
                .ifPresentOrElse(
                        usuario -> usuarioRepository.marcarExclusao(usuarioId),
                        () -> { throw new NegocioException("Usuário não encontrado"); }
                );
    }

    @Override
    @Transactional
    public void trocarSenha(UUID usuarioId, String senhaAtual, String novaSenha) {
        usuarioRepository.findById(usuarioId)
                .filter(usuario -> passwordEncoder.matches(senhaAtual, usuario.getSenha()))
                .ifPresentOrElse(
                        usuario -> usuarioRepository.updateSenha(usuarioId, passwordEncoder.encode(novaSenha)),
                        () -> { throw new NegocioException("Senha atual inválida"); }
                );
    }

    @Override
    public void solicitarRecuperacaoSenha(String email) {
        usuarioRepository.findByEmail(email)
                .ifPresentOrElse(
                        usuario -> {
                            String token = UUID.randomUUID().toString();
                            usuario.setTokenRecuperacao(token);
                            usuario.setTokenRecuperacaoExpiraEm(LocalDateTime.now().plusHours(1));
                            usuarioRepository.save(usuario);
                            // TODO: Enviar email com token
                        },
                        () -> { throw new NegocioException("Email não encontrado"); }
                );
    }

    @Override
    @Transactional
    public void redefinirSenha(String token, String novaSenha) {
        usuarioRepository.findByTokenRecuperacao(token)
                .filter(usuario -> usuario.getTokenRecuperacaoExpiraEm().isAfter(LocalDateTime.now()))
                .ifPresentOrElse(
                        usuario -> {
                            usuario.setSenha(passwordEncoder.encode(novaSenha));
                            usuario.setTokenRecuperacao(null);
                            usuario.setTokenRecuperacaoExpiraEm(null);
                            usuarioRepository.save(usuario);
                        },
                        () -> { throw new NegocioException("Token inválido ou expirado"); }
                );
    }

    @Override
    public String renovarToken(String refreshToken) {
        return jwtService.renovarToken(refreshToken);
    }

    private void validarNovoUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new NegocioException("Email já cadastrado");
        }
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new NegocioException("CPF já cadastrado");
        }
    }

    private void validarAdmin(UUID adminId) {
        usuarioRepository.findById(adminId)
                .filter(admin -> "ADMINISTRADOR".equals(admin.getPerfil().name()))
                .orElseThrow(() -> new NegocioException("Permissão negada"));
    }

    private void atualizarDadosUsuario(Usuario usuario, Usuario dadosAtualizacao) {
        usuario.setPrimeiroNome(dadosAtualizacao.getPrimeiroNome());
        usuario.setUltimoNome(dadosAtualizacao.getUltimoNome());
        usuario.setTelefone(dadosAtualizacao.getTelefone());
        usuario.setDataNascimento(dadosAtualizacao.getDataNascimento());
        usuario.setEndereco(dadosAtualizacao.getEndereco());
        usuario.setDataAlteracao(LocalDateTime.now());
    }
}
