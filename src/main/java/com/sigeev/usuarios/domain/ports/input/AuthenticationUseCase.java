package com.sigeev.usuarios.domain.ports.input;

public interface AuthenticationUseCase {
    String authenticate(String email, String senha);
}
