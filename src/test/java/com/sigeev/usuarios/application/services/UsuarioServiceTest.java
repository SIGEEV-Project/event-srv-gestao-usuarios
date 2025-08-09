package com.sigeev.usuarios.application.services;

import com.sigeev.usuarios.domain.entities.Usuario;
import com.sigeev.usuarios.domain.entities.PerfilUsuario;
import com.sigeev.usuarios.domain.exceptions.NegocioException;
import com.sigeev.usuarios.domain.ports.UsuarioRepositoryPort;
import com.sigeev.usuarios.domain.ports.EmailServicePort;
import com.sigeev.usuarios.infrastructure.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private EmailServicePort emailService;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioMock;
    private String senhaHash;

    @BeforeEach
    void setUp() {
        usuarioMock = Usuario.builder()
                .usuarioId(UUID.randomUUID())
                .primeiroNome("João")
                .ultimoNome("Silva")
                .email("joao@email.com")
                .cpf("12345678900")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .perfil(PerfilUsuario.PARTICIPANTE)
                .build();

        senhaHash = "senhaHash";
    }

    @Test
    void cadastrarUsuario_QuandoDadosValidos_DeveSalvarUsuario() {
        // Arrange
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.existsByCpf(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn(senhaHash);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);

        // Act
        Usuario resultado = usuarioService.cadastrarUsuario(usuarioMock, "senha123");

        // Assert
        assertNotNull(resultado);
        assertEquals(usuarioMock.getEmail(), resultado.getEmail());
        verify(emailService).enviarEmailConfirmacaoCadastro(anyString(), anyString());
    }

    @Test
    void cadastrarUsuario_QuandoEmailJaExiste_DeveLancarExcecao() {
        // Arrange
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(NegocioException.class, () ->
            usuarioService.cadastrarUsuario(usuarioMock, "senha123")
        );
    }

    @Test
    void autenticarUsuario_QuandoCredenciaisValidas_DeveRetornarToken() {
        // Arrange
        String senha = "senha123";
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuarioMock));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtService.gerarToken(any(Usuario.class))).thenReturn("token");

        // Act
        String token = usuarioService.autenticarUsuario(usuarioMock.getEmail(), senha);

        // Assert
        assertNotNull(token);
        verify(usuarioRepository).registrarLogin(any(UUID.class));
    }

    @Test
    void promoverUsuario_QuandoAdminValido_DevePromoverUsuario() {
        // Arrange
        Usuario admin = Usuario.builder()
                .usuarioId(UUID.randomUUID())
                .perfil(PerfilUsuario.ADMINISTRADOR)
                .build();

        when(usuarioRepository.findById(admin.getUsuarioId())).thenReturn(Optional.of(admin));
        when(usuarioRepository.findById(usuarioMock.getUsuarioId())).thenReturn(Optional.of(usuarioMock));

        // Act
        usuarioService.promoverUsuario(usuarioMock.getUsuarioId(), admin.getUsuarioId(), "PROMOTOR");

        // Assert
        verify(usuarioRepository).atualizarPerfil(usuarioMock.getUsuarioId(), "PROMOTOR");
        verify(emailService).enviarEmailAlteracaoPerfil(anyString(), anyString(), anyString());
    }

    @Test
    void excluirConta_QuandoUsuarioExiste_DeveMarcarComoExcluido() {
        // Arrange
        when(usuarioRepository.findById(any(UUID.class))).thenReturn(Optional.of(usuarioMock));

        // Act
        usuarioService.excluirConta(usuarioMock.getUsuarioId());

        // Assert
        verify(usuarioRepository).marcarExclusao(usuarioMock.getUsuarioId());
        verify(emailService).enviarEmailExclusaoConta(anyString(), anyString());
    }
}
