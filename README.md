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

## Stack desta versão

Backend **Java + Spring Boot** (`backend/`), banco **PostgreSQL**, frontend **SvelteKit em modo SPA** (`web/`). E-mails transacionais via provedor externo atrás de uma interface própria. Detalhes e porquês em [docs/04-implementacao.md](docs/04-implementacao.md).

Os docs de arquitetura definem **o quê** e **por quê** — não **como**. Estrutura de pastas, bibliotecas e detalhes de implementação são decisão de quem implementa.

## Rodando localmente

Pré-requisitos: Docker, JDK 25 e Node 20+.

```bash
# 1. Variáveis de ambiente (os defaults já funcionam pra dev)
cp .env.example .env

# 2. Infra — Postgres em localhost:5432
docker compose up -d

# 3. Backend — http://localhost:8080
#    Pela IDE: rode BackendApplication. Pelo terminal:
cd backend && ./mvnw spring-boot:run

# 4. Front — http://localhost:5173 (proxy /api -> :8080)
cd web && npm install && npm run dev
```

Funcionou se `localhost:5173` mostrar **API UP** — a resposta atravessa Vite → Spring → Postgres.

Testes: `./mvnw test` no `backend/`, `npm test` no `web/`. Derrubar a infra: `docker compose down` (com `-v` apaga o banco).
