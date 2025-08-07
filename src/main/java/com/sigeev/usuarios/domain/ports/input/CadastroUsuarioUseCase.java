package com.sigeev.usuarios.domain.ports.input;

import com.sigeev.usuarios.domain.entities.Usuario;

public interface CadastroUsuarioUseCase {
    Usuario cadastrarNovoUsuario(Usuario usuario);
}
