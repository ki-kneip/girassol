# ADR-007: Redis entra no MVP, só para sessões

**Status:** Aceita — supera a [ADR-006](ADR-006-redis-fora-do-mvp.md)

## Contexto
A ADR-006 mandava sessões para o Postgres. Na hora de implementar (M1), pesou a preferência de não usar banco relacional para sessão — e sessão é o caso de uso clássico do Redis: chave-valor com TTL nativo (expira sozinha, sem job de limpeza).

## Decisão
Redis entra na infra de desenvolvimento e a sessão vive nele via Spring Session (`spring-session-data-redis`). **Escopo estrito:** só sessão. Cache, filas e rate limiting continuam fora até doer — essa parte da ADR-006 segue valendo em espírito.

## Por quê
O custo é operacional e pequeno (um container a mais no compose; um serviço a mais em produção um dia); no código, a diferença para a alternativa JDBC é qual starter está no pom. Em troca: TTL nativo, sessões fora do banco de dados de domínio, e o restart do backend em dev não desloga ninguém.

## Gatilho para revisitar
Se o Redis virar tentação para "cachear tudo": cada uso novo dele merece discussão própria (e possivelmente um ADR).
