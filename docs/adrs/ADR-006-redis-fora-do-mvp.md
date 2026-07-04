# ADR-006: Redis fora do MVP

**Status:** Aceita

## Contexto
Redis apareceu como candidato para sessões, cache e tokens.

## Decisão
Fora do MVP. Sessões, tokens e cache vivem no Postgres por ora.

## Por quê
Na escala do MVP, Postgres resolve tudo isso com uma dependência a menos para operar.

## Gatilho para revisitar
Rate limiting sério, cache com TTL agressivo, ou filas.
