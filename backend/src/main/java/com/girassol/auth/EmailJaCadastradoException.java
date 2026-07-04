package com.girassol.auth;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException() {
        super("Este e-mail já está cadastrado.");
    }
}
