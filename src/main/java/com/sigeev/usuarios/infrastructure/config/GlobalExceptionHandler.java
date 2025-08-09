package com.sigeev.usuarios.infrastructure.config;

import com.sigeev.usuarios.adapters.inbound.dtos.ApiResponse;
import com.sigeev.usuarios.domain.exceptions.NegocioException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ApiResponse> handleNegocioException(NegocioException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                        .sucesso(false)
                        .mensagem(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ApiResponse.ErroResponse> erros = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    String campo = ((FieldError) error).getField();
                    String mensagem = error.getDefaultMessage();
                    return ApiResponse.ErroResponse.builder()
                            .campo(campo)
                            .mensagem(mensagem)
                            .build();
                })
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                        .sucesso(false)
                        .mensagem("Erro de validação")
                        .erros(erros)
                        .build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.builder()
                        .sucesso(false)
                        .mensagem("Credenciais inválidas")
                        .build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.builder()
                        .sucesso(false)
                        .mensagem("Acesso negado")
                        .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ApiResponse.ErroResponse> erros = ex.getConstraintViolations()
                .stream()
                .map(violation -> ApiResponse.ErroResponse.builder()
                        .campo(violation.getPropertyPath().toString())
                        .mensagem(violation.getMessage())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                        .sucesso(false)
                        .mensagem("Erro de validação")
                        .erros(erros)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.builder()
                        .sucesso(false)
                        .mensagem("Erro interno do servidor")
                        .build());
    }
}
