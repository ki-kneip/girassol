package com.girassol.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.girassol.espaco.Espaco;
import com.girassol.espaco.EspacoRepository;
import com.girassol.espaco.Membership;
import com.girassol.espaco.MembershipRepository;
import com.girassol.espaco.Role;
import com.girassol.usuario.Usuario;
import com.girassol.usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CadastroService {

    private final UsuarioRepository usuarios;
    private final EspacoRepository espacos;
    private final MembershipRepository memberships;
    private final PasswordEncoder passwordEncoder;

    /**
     * Regra central do cadastro: usuário, Espaço pessoal e membership DONO
     * nascem juntos ou não nasce nada (doc 01 / ADR-001).
     */
    @Transactional
    public Usuario cadastrar(String nome, String email, String senha) {
        if (usuarios.existsByEmail(email)) {
            throw new EmailJaCadastradoException();
        }
        Usuario usuario = usuarios.save(new Usuario(nome, email, passwordEncoder.encode(senha)));
        Espaco pessoal = espacos.save(new Espaco("Pessoal", true));
        memberships.save(new Membership(usuario, pessoal, Role.DONO));
        return usuario;
    }
}
