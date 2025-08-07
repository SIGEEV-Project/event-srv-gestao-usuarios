package com.sigeev.usuarios.domain.ports.input;

import com.sigeev.usuarios.domain.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface ConsultaUsuarioUseCase {
    Usuario buscarPorId(UUID id);
    Usuario buscarPorEmail(String email);
    Page<Usuario> listarUsuarios(Pageable paginacao, String filtro);
    void excluirUsuario(UUID id);
    void reativarUsuario(UUID id);
}
