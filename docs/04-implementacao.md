# Implementação — versão ki-kneip

Os docs 01–03 valem para qualquer implementação e deixam o "como" em aberto de propósito. **Este doc fecha essas escolhas para a versão deste repositório.** Se você está implementando a sua própria versão, ele serve no máximo de inspiração — não de regra.

## Escolhas fechadas

| Aberto no doc 02 | Escolha aqui |
|---|---|
| Migrações de banco | **Flyway** (SQL puro em `backend/src/main/resources/db/migration`, `V1__descricao.sql`) |
| Frontend | **SvelteKit em modo SPA** (`adapter-static`, `ssr = false`) — Svelte 5; SSR fica desligado porque quem serve dados é a API Spring |
| Estrutura | **Monorepo**, backend por feature (abaixo) |
| Idioma do código | **Domínio em português, técnico em inglês** (`EspacoController`, `ConviteService`) — código e docs falam a mesma língua |

Futuro já sinalizado: mobile em KMP + Compose Multiplatform, desktop com Wails, Redis só se um gatilho do ADR-006 disparar. Nada disso influencia a estrutura agora — a API REST é o contrato.

## Estrutura de pastas

```
girassol/
├── docs/                     # estes documentos
├── compose.yaml              # Postgres para desenvolvimento
├── backend/                  # Spring Boot
│   └── src/main/
│       ├── java/com/girassol/
│       │   ├── comum/        # config, tratamento global de erros, segurança base
│       │   ├── auth/         # cadastro, login, sessão, recuperar/redefinir senha
│       │   ├── usuario/      # perfil, /me
│       │   ├── espaco/       # Espaço + Membership + can()  ← o coração
│       │   ├── convite/
│       │   ├── email/        # EmailService + impls (console, Brevo/Resend)
│       │   └── apps/
│       │       ├── notas/
│       │       ├── tarefas/
│       │       ├── compras/
│       │       └── financeiro/
│       └── resources/
│           └── db/migration/ # Flyway
└── web/                      # SvelteKit em modo SPA
    └── src/
        ├── routes/           # rotas por arquivo (+page.svelte) — espelham as telas
        └── lib/              # importável via $lib
            ├── api/          # cliente HTTP da API
            └── components/   # UI compartilhada
```

### Regras da estrutura

- **Pacote por feature, não por camada.** Controller, service, repository e entidades de uma feature vivem juntos no módulo dela. Sem pastas globais `controllers/`, `services/`.
- **`espaco/` é o módulo central**: dono do `can()`. Qualquer módulo que precise de autorização importa de lá — a dependência fica explícita e num sentido só (apps → espaco, nunca o contrário).
- **Cada tipo de App é um clone estrutural de `notas/`**: mesma anatomia interna. O primeiro (M1) define o padrão; os demais (M4/M5) copiam.
- **Monorepo porque as features são fatias verticais**: um PR carrega migração + endpoint + tela. Clientes futuros (KMP/Wails) decidem depois se entram aqui ou em repo próprio.
- `comum/` é para o que é genuinamente transversal (config, erro, segurança). Se algo "comum" só tem um usuário, pertence ao módulo desse usuário.

## Convenções

- Migrações Flyway: SQL puro, uma mudança lógica por arquivo, nome descritivo (`V3__cria_convite.sql`). Migração aplicada em `dev` **não se edita** — cria-se a próxima.
- Branches: `main` estável, `dev` como linha de desenvolvimento, features em branches curtas mergeadas via PR em `dev`.
