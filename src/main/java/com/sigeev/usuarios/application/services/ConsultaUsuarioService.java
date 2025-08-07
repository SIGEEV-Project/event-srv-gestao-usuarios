package com.sigeev.usuarios.application.services;

import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.entities.enums.StatusUsuario;
import com.sigeev.usuarios.domain.ports.input.ConsultaUsuarioUseCase;
import com.sigeev.usuarios.domain.ports.output.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsultaUsuarioService implements ConsultaUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> listarUsuarios(Pageable paginacao, String filtro) {
        return usuarioRepository.buscarPorFiltro(filtro, paginacao);
    }

    @Override
    @Transactional
    public void excluirUsuario(UUID id) {
        Usuario usuario = buscarPorId(id);
        
        if (usuario.getPerfil().toString().equals("ADMINISTRADOR")) {
            throw new AccessDeniedException("Não é permitido excluir usuários administradores");
        }

        usuario.setStatus(StatusUsuario.INATIVO);
        usuario.setDataExclusao(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void reativarUsuario(UUID id) {
        Usuario usuario = buscarPorId(id);
        
        if (usuario.getStatus() != StatusUsuario.INATIVO) {
            throw new IllegalArgumentException("Usuário já está ativo");
        }

        usuario.setStatus(StatusUsuario.ATIVO);
        usuario.setDataReativacao(LocalDateTime.now());
        usuario.setDataExclusao(null);
        usuarioRepository.save(usuario);
    }
}
