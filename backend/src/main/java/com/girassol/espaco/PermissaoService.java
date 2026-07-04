package com.girassol.espaco;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * ADR-002: o ÚNICO ponto de decisão de autorização do sistema.
 * Nenhum controller/service checa role diretamente — todo mundo pergunta aqui.
 */
@Service
@RequiredArgsConstructor
public class PermissaoService {

    private final MembershipRepository memberships;

    @Transactional(readOnly = true)
    public boolean can(UUID usuarioId, Acao acao, Espaco espaco) {
        return memberships.findByUsuarioIdAndEspacoId(usuarioId, espaco.getId())
                .map(membership -> permitidoNoEspaco(membership.getRole(), acao, espaco))
                .orElse(false);
    }

    private boolean permitidoNoEspaco(Role role, Acao acao, Espaco espaco) {
        // Invariantes do Espaço pessoal (ADR-001): sem convites, sem exclusão — nem pro DONO
        if (espaco.isPessoal() && (acao == Acao.MEMBRO_CONVIDAR || acao == Acao.ESPACO_EXCLUIR)) {
            return false;
        }
        return role.permite(acao);
    }
}
