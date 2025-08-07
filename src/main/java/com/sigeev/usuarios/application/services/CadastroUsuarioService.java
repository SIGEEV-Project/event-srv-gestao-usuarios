package com.sigeev.usuarios.application.services;

import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.ports.input.CadastroUsuarioUseCase;
import com.sigeev.usuarios.domain.ports.output.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastroUsuarioService implements CadastroUsuarioUseCase {
    
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public Usuario cadastrarNovoUsuario(Usuario usuario) {
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    private void validarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
    }
}
