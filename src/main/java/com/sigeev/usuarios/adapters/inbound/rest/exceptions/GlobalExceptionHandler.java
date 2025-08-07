package com.sigeev.usuarios.adapters.inbound.rest.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse response = ErrorResponse.builder()
                .sucesso(false)
                .mensagem("Erro ao cadastrar usuário.")
                .erros(List.of(new ErrorDetail(null, e.getMessage())))
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        List<ErrorDetail> erros = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            erros.add(new ErrorDetail(error.getField(), error.getDefaultMessage()));
        }

        ErrorResponse response = ErrorResponse.builder()
                .sucesso(false)
                .mensagem("Dados inválidos no formulário.")
                .erros(erros)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorResponse {
        private boolean sucesso;
        private String mensagem;
        private List<ErrorDetail> erros;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorDetail {
        private String campo;
        private String mensagem;
    }
}
