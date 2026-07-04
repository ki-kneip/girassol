package com.girassol.espaco;

/**
 * Tudo que se pode fazer num Espaço. A tabela role -> ações vive em {@link Role};
 * a decisão final (incluindo invariantes do Espaço pessoal) em {@link PermissaoService}.
 */
public enum Acao {
    CONTEUDO_GERENCIAR,
    APP_CRIAR,
    APP_GERENCIAR,
    MEMBRO_CONVIDAR,
    MEMBRO_REMOVER,
    MEMBRO_ALTERAR_ROLE,
    ESPACO_EDITAR,
    ESPACO_EXCLUIR,
    POSSE_TRANSFERIR
}
