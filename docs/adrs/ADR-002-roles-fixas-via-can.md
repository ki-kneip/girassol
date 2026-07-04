# ADR-002: Roles fixas agora, RBAC granular talvez depois

**Status:** Aceita

## Contexto
Precisamos de permissões por Espaço. Roles simples pré-definidas ou sistema granular (RBAC completo) desde o início?

## Decisão
Três roles fixas (`DONO`, `ADMIN`, `MEMBRO`) hardcoded, mas **toda** checagem de autorização passa pela função única `can(usuario, acao, espaco)`. Proibido checar role diretamente em controller/service.

## Por quê
RBAC granular é semanas de infraestrutura antes da primeira feature visível — veneno para um projeto que precisa de tração. A função `can` é a apólice de seguro: se o granular vier, só o interior dela muda; nenhum endpoint é tocado.

## Gatilho para revisitar
Usuários reais pedindo "quero que fulano só veja o app X".
