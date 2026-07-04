package com.girassol.espaco;

import java.util.EnumSet;
import java.util.Set;

/**
 * Tabela de permissões do doc 01-visao-e-dominio.md, em código.
 * ADR-002: roles fixas por ora; se um dia virar RBAC granular,
 * muda o interior do PermissaoService — este enum e seus usos somem juntos.
 */
public enum Role {

    MEMBRO(EnumSet.of(
            Acao.CONTEUDO_GERENCIAR,
            Acao.APP_CRIAR)),

    ADMIN(EnumSet.of(
            Acao.CONTEUDO_GERENCIAR,
            Acao.APP_CRIAR,
            Acao.APP_GERENCIAR,
            Acao.MEMBRO_CONVIDAR,
            Acao.MEMBRO_REMOVER,
            Acao.ESPACO_EDITAR)),

    DONO(EnumSet.allOf(Acao.class));

    private final Set<Acao> acoes;

    Role(Set<Acao> acoes) {
        this.acoes = acoes;
    }

    boolean permite(Acao acao) {
        return acoes.contains(acao);
    }
}
