package com.girassol.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroRequest(
        @NotBlank(message = "Nome é obrigatório") @Size(max = 120) String nome,
        @NotBlank(message = "E-mail é obrigatório") @Email(message = "E-mail inválido") @Size(max = 255) String email,
        @NotBlank(message = "Senha é obrigatória") @Size(min = 8, max = 72, message = "Senha deve ter entre 8 e 72 caracteres") String senha) {
}
