package com.girassol.espaco;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

/**
 * A tabela role -> ações do doc 01-visao-e-dominio.md, verificada em código.
 * Se este teste quebrar, ou o doc mudou (atualize o teste) ou alguém mexeu
 * sem mudar o doc (conversa antes de codar).
 */
class RoleTest {

    @Test
    void donoPodeTudo() {
        for (Acao acao : Acao.values()) {
            assertThat(Role.DONO.permite(acao)).as("DONO deve poder %s", acao).isTrue();
        }
    }

    @Test
    void adminNaoTocaEmRolesNemNaExistenciaDoEspaco() {
        var vetadas = EnumSet.of(Acao.MEMBRO_ALTERAR_ROLE, Acao.ESPACO_EXCLUIR, Acao.POSSE_TRANSFERIR);
        for (Acao acao : Acao.values()) {
            assertThat(Role.ADMIN.permite(acao))
                    .as("ADMIN e a ação %s", acao)
                    .isEqualTo(!vetadas.contains(acao));
        }
    }

    @Test
    void membroSoUsaConteudoECriaApps() {
        var permitidas = EnumSet.of(Acao.CONTEUDO_GERENCIAR, Acao.APP_CRIAR);
        for (Acao acao : Acao.values()) {
            assertThat(Role.MEMBRO.permite(acao))
                    .as("MEMBRO e a ação %s", acao)
                    .isEqualTo(permitidas.contains(acao));
        }
    }
}
