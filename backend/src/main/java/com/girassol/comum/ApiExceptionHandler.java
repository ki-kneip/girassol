package com.girassol.comum;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.girassol.auth.EmailJaCadastradoException;

/**
 * Formato de erro do contrato (doc 02): { "erro": "codigo", "mensagem": "..." }
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    public record ErroResponse(String erro, String mensagem) {
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResponse dadosInvalidos(MethodArgumentNotValidException e) {
        String mensagem = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(erro -> erro.getDefaultMessage())
                .orElse("Dados inválidos.");
        return new ErroResponse("dados_invalidos", mensagem);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErroResponse credenciaisInvalidas() {
        return new ErroResponse("credenciais_invalidas", "E-mail ou senha incorretos.");
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResponse emailJaCadastrado(EmailJaCadastradoException e) {
        return new ErroResponse("email_ja_cadastrado", e.getMessage());
    }
}
