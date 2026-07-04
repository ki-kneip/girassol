# ADR-001: Espaço pessoal é um Espaço comum com flag

**Status:** Aceita

## Contexto
Todo usuário tem um Espaço pessoal (privado, sem convites) e pode criar Espaços compartilhados. Seriam dois conceitos ou um?

## Decisão
Uma entidade só, com `pessoal = true` bloqueando convites e exclusão.

## Por quê
Dois conceitos dobrariam o código (queries, permissões, UI) para um comportamento 95% idêntico. A flag custa dois `if`s.
