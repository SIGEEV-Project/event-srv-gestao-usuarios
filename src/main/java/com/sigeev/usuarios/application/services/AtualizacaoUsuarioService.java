package com.sigeev.usuarios.application.services;

import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.ports.input.AtualizacaoUsuarioUseCase;
import com.sigeev.usuarios.domain.ports.output.UsuarioRepository;
import com.sigeev.usuarios.domain.ports.output.SenhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AtualizacaoUsuarioService implements AtualizacaoUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final SenhaRepository senhaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Usuario atualizarDados(UUID usuarioId, Usuario dadosAtualizacao) {
        Usuario usuarioExistente = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (dadosAtualizacao.getEmail() != null && 
            !dadosAtualizacao.getEmail().equals(usuarioExistente.getEmail()) && 
            usuarioRepository.existsByEmail(dadosAtualizacao.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        atualizarCamposUsuario(usuarioExistente, dadosAtualizacao);
        return usuarioRepository.save(usuarioExistente);
    }

    @Override
    @Transactional
    public Usuario atualizarSenha(UUID usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        String senhaHash = senhaRepository.buscarSenhaHash(usuarioId);
        if (!passwordEncoder.matches(senhaAtual, senhaHash)) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }

        senhaRepository.salvarSenha(usuarioId, passwordEncoder.encode(novaSenha));
        return usuario;
    }

    private void atualizarCamposUsuario(Usuario usuarioExistente, Usuario dadosAtualizacao) {
        if (dadosAtualizacao.getPrimeiroNome() != null) {
            usuarioExistente.setPrimeiroNome(dadosAtualizacao.getPrimeiroNome());
        }
        if (dadosAtualizacao.getUltimoNome() != null) {
            usuarioExistente.setUltimoNome(dadosAtualizacao.getUltimoNome());
        }
        if (dadosAtualizacao.getTelefone() != null) {
            usuarioExistente.setTelefone(dadosAtualizacao.getTelefone());
        }
        if (dadosAtualizacao.getEmail() != null) {
            usuarioExistente.setEmail(dadosAtualizacao.getEmail());
        }
        if (dadosAtualizacao.getEndereco() != null) {
            if (dadosAtualizacao.getEndereco().getLogradouro() != null) {
                usuarioExistente.getEndereco().setLogradouro(dadosAtualizacao.getEndereco().getLogradouro());
            }
            if (dadosAtualizacao.getEndereco().getNumero() != null) {
                usuarioExistente.getEndereco().setNumero(dadosAtualizacao.getEndereco().getNumero());
            }
            if (dadosAtualizacao.getEndereco().getCidade() != null) {
                usuarioExistente.getEndereco().setCidade(dadosAtualizacao.getEndereco().getCidade());
            }
            if (dadosAtualizacao.getEndereco().getEstado() != null) {
                usuarioExistente.getEndereco().setEstado(dadosAtualizacao.getEndereco().getEstado());
            }
            if (dadosAtualizacao.getEndereco().getCep() != null) {
                usuarioExistente.getEndereco().setCep(dadosAtualizacao.getEndereco().getCep());
            }
        }
        if (dadosAtualizacao.getContato() != null) {
            if (dadosAtualizacao.getContato().getTelefone() != null) {
                usuarioExistente.getContato().setTelefone(dadosAtualizacao.getContato().getTelefone());
            }
            if (dadosAtualizacao.getContato().getEmailContato() != null) {
                usuarioExistente.getContato().setEmailContato(dadosAtualizacao.getContato().getEmailContato());
            }
        }

        usuarioExistente.setDataAlteracao(LocalDateTime.now());
    }
}
