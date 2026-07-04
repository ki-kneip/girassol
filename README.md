# 🌻 Girassol

> **Gira em torno do que importa.**

Girassol é um aplicativo que reúne vários aspectos da vida em um lugar só: tarefas, notas, controle financeiro e lista de compras — organizados em **Espaços** pessoais ou compartilhados.

*Organize sua vida em torno do que importa.*

## Conceito em 30 segundos

- Todo usuário nasce com um **Espaço pessoal** (privado, não aceita convites).
- O usuário pode criar outros Espaços e **convidar pessoas** para eles.
- Dentro de um Espaço, criam-se **Apps**: Notas, Tarefas, Financeiro, Lista de Compras — quantos quiser, de qualquer tipo.
- Cada membro de um Espaço tem uma **role**: Dono, Admin ou Membro.

## Documentação

| Doc | O que tem |
|---|---|
| [Visão e Domínio](docs/01-visao-e-dominio.md) | Conceitos, regras de negócio, o que cada App faz |
| [Arquitetura](docs/02-arquitetura.md) | Modelo de dados, permissões, convites, contratos de API, ADRs |
| [Roadmap](docs/03-roadmap.md) | Fatias verticais de implementação, milestone a milestone |
| [Implementação](docs/04-implementacao.md) | Escolhas da versão deste repo: estrutura de pastas, Flyway, convenções |

## Stack de referência

Backend **Java + Spring Boot**, banco **PostgreSQL**, frontend **React + Vite** (web primeiro). E-mails transacionais via provedor externo (Brevo ou Resend) atrás de uma interface própria.

Os docs de arquitetura definem **o quê** e **por quê** — não **como**. Estrutura de pastas, bibliotecas e detalhes de implementação são decisão de quem implementa.
