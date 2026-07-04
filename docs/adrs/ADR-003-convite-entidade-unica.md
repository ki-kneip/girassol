# ADR-003: Convite é uma entidade única com três modos de entrega

**Status:** Aceita

## Contexto
Convites chegam por três caminhos: in-platform (aceitar/recusar no app), e-mail com magic link, e magic link copiável.

## Decisão
Os três modos são o **mesmo registro** (`convite`); muda apenas se o token é exposto e para quem. Ciclo de vida único: `PENDENTE → ACEITO | RECUSADO | REVOGADO | EXPIRADO`. No MVP, convite é de uso único (aceito uma vez, morre).

## Por quê
Três fluxos separados = três máquinas de estado para manter. Uma entidade = uma tabela, um ciclo de vida, e cada modo pode ser implementado em milestone diferente sem migração.

## Gatilho para revisitar
Se criar N convites para N pessoas doer na prática, evoluir para convite multiuso com contador.
