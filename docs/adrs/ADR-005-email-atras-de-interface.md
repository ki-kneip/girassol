# ADR-005: Provedor de e-mail atrás de interface própria

**Status:** Aceita

## Contexto
E-mails transacionais (boas-vindas, recuperar senha, convite) precisam de um provedor externo — Brevo e Resend são os candidatos.

## Decisão
A aplicação define `EmailService.enviar(destinatario, template, dados)`; Brevo, Resend e uma implementação "console" (dev) são detalhes de infraestrutura atrás dessa interface.

## Por quê
E-mail transacional é commodity e free tiers mudam; ninguém deve reescrever fluxo de senha porque trocou de provedor. Bônus: em desenvolvimento, roda sem conta externa.
