package com.girassol.usuario;

import java.util.UUID;

public record UsuarioResponse(UUID id, String nome, String email) {

    public static UsuarioResponse de(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
