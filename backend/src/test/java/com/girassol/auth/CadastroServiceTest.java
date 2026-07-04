package com.girassol.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import com.girassol.TestcontainersConfiguration;
import com.girassol.espaco.Acao;
import com.girassol.espaco.Membership;
import com.girassol.espaco.MembershipRepository;
import com.girassol.espaco.PermissaoService;
import com.girassol.espaco.Role;
import com.girassol.usuario.Usuario;
import com.girassol.usuario.UsuarioRepository;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@Transactional // cada teste roda numa transação (lazy loading funciona) e faz rollback no fim
class CadastroServiceTest {

    @Autowired
    CadastroService cadastroService;
    @Autowired
    UsuarioRepository usuarios;
    @Autowired
    MembershipRepository memberships;
    @Autowired
    PermissaoService permissoes;

    @Test
    void cadastroCriaUsuarioComEspacoPessoalEDono() {
        Usuario usuario = cadastroService.cadastrar("Ana", "ana@girassol.dev", "senha-forte");

        List<Membership> doUsuario = memberships.findByUsuarioId(usuario.getId());
        assertThat(doUsuario).hasSize(1);

        Membership membership = doUsuario.getFirst();
        assertThat(membership.getRole()).isEqualTo(Role.DONO);
        assertThat(membership.getEspaco().isPessoal()).isTrue();

        // Senha nunca em texto puro
        assertThat(usuario.getSenhaHash()).isNotEqualTo("senha-forte").startsWith("$2");
    }

    @Test
    void espacoPessoalNaoAceitaConviteNemExclusaoNemPeloDono() {
        Usuario usuario = cadastroService.cadastrar("Bia", "bia@girassol.dev", "senha-forte");
        var espacoPessoal = memberships.findByUsuarioId(usuario.getId()).getFirst().getEspaco();

        assertThat(permissoes.can(usuario.getId(), Acao.CONTEUDO_GERENCIAR, espacoPessoal)).isTrue();
        assertThat(permissoes.can(usuario.getId(), Acao.APP_CRIAR, espacoPessoal)).isTrue();
        // Invariantes do ADR-001
        assertThat(permissoes.can(usuario.getId(), Acao.MEMBRO_CONVIDAR, espacoPessoal)).isFalse();
        assertThat(permissoes.can(usuario.getId(), Acao.ESPACO_EXCLUIR, espacoPessoal)).isFalse();
    }

    @Test
    void quemNaoEMembroNaoPodeNada() {
        Usuario dona = cadastroService.cadastrar("Clara", "clara@girassol.dev", "senha-forte");
        Usuario intruso = cadastroService.cadastrar("Davi", "davi@girassol.dev", "senha-forte");
        var espacoDaClara = memberships.findByUsuarioId(dona.getId()).getFirst().getEspaco();

        for (Acao acao : Acao.values()) {
            assertThat(permissoes.can(intruso.getId(), acao, espacoDaClara))
                    .as("não-membro e a ação %s", acao)
                    .isFalse();
        }
    }

    @Test
    void emailDuplicadoERejeitado() {
        cadastroService.cadastrar("Eva", "eva@girassol.dev", "senha-forte");
        long usuariosAntes = usuarios.count();

        assertThatExceptionOfType(EmailJaCadastradoException.class)
                .isThrownBy(() -> cadastroService.cadastrar("Eva 2", "eva@girassol.dev", "outra-senha"));

        assertThat(usuarios.count()).isEqualTo(usuariosAntes);
    }
}
