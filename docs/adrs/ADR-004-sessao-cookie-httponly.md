# ADR-004: Sessão via cookie httpOnly no web (não JWT em localStorage)

**Status:** Aceita

## Contexto
O web precisa de autenticação persistente entre requisições.

## Decisão
Sessão em servidor, entregue via cookie httpOnly. Proteção CSRF habilitada (o Spring Security já traz).

## Por quê
Cookie httpOnly é imune a roubo por XSS, e sessão em servidor permite logout/revogação de verdade. JWT brilha em API pública ou microsserviços — não é o nosso caso.

## Nota para o futuro
Quando o mobile chegar, a API pode passar a aceitar também um token opaco no header, sem quebrar o web.
