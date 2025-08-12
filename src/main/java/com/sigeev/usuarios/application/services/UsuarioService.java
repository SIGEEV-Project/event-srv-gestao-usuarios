package com.sigeev.usuarios.application.services;

import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.ports.inbound.UsuarioServicePort;
import com.sigeev.usuarios.domain.ports.outbound.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioServicePort {

    private final UsuarioRepositoryPort usuarioRepository;

    @Override
    @Transactional
    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        usuario.setStatus("ativo");
        usuario.setDataCadastro(LocalDateTime.now());
        
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Optional<Usuario> buscarPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }

    @Override
    @Transactional
    public void atualizarUsuario(Usuario usuario) {
        usuarioRepository.findById(usuario.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        usuario.setDataAlteracao(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void excluirUsuario(UUID id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        usuario.setStatus("inativo");
        usuario.setDataExclusao(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void reativarUsuario(UUID id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        usuario.setStatus("ativo");
        usuario.setDataReativacao(LocalDateTime.now());
        usuario.setDataExclusao(null);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void atualizarSenha(UUID id, String novaSenha) {
        // A implementação da atualização de senha será feita na camada de segurança
        throw new UnsupportedOperationException("Não implementado");
    }

    @Override
    @Transactional
    public void solicitarRecuperacaoSenha(String email) {
        var usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email não encontrado"));

        // Gerar token de recuperação
        String token = UUID.randomUUID().toString();
        usuario.setTokenRecuperacao(token);
        usuario.setTokenRecuperacaoExpiraEm(LocalDateTime.now().plusHours(1));
        
        usuarioRepository.save(usuario);
        
        // TODO: Enviar email com token de recuperação
    }

    @Override
    @Transactional
    public void recuperarSenha(String token, String novaSenha) {
        // A implementação da recuperação de senha será feita na camada de segurança
        throw new UnsupportedOperationException("Não implementado");
    }
}
