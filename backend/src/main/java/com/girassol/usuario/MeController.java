package com.girassol.usuario;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MeController {

    private final UsuarioRepository usuarios;

    @GetMapping("/me")
    public UsuarioResponse me(Authentication authentication) {
        // O principal guarda o e-mail (ver UsuarioDetailsService)
        Usuario usuario = usuarios.findByEmail(authentication.getName()).orElseThrow();
        return UsuarioResponse.de(usuario);
    }
}
