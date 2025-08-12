package com.sigeev.usuarios.adapters.inbound.rest.handler;

import com.sigeev.usuarios.adapters.inbound.rest.dto.RespostaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespostaDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<RespostaDTO.ErroDTO> erros = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String campo = error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName();
                    String mensagem = error.getDefaultMessage();
                    return RespostaDTO.ErroDTO.builder()
                            .campo(campo)
                            .mensagem(mensagem)
                            .build();
                })
                .collect(Collectors.toList());

        var resposta = RespostaDTO.builder()
                .sucesso(false)
                .mensagem("Erro de validação")
                .erros(erros)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RespostaDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        var erro = RespostaDTO.ErroDTO.builder()
                .mensagem(ex.getMessage())
                .build();

        var resposta = RespostaDTO.builder()
                .sucesso(false)
                .mensagem("Erro de validação")
                .erros(List.of(erro))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespostaDTO> handleGenericException(Exception ex) {
        var erro = RespostaDTO.ErroDTO.builder()
                .mensagem("Ocorreu um erro interno no servidor")
                .build();

        var resposta = RespostaDTO.builder()
                .sucesso(false)
                .mensagem("Erro interno do servidor")
                .erros(List.of(erro))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }
}
