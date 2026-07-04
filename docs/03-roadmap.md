# Roadmap — fatias verticais

Cada milestone é uma fatia **de ponta a ponta** (banco → API → tela) que termina com algo usável. Nada de "fazer todo o backend primeiro": ver o app funcionando cedo é o que mantém o projeto vivo, e cada fatia vira um PR (ou uma sequência curta de PRs) revisável.

Dentro de cada milestone, a ordem sugerida é: modelo/migração → endpoint com teste → tela.

## M0 — Esqueleto andante
**Resultado: front, back e banco de pé, conversando entre si, com um fluxo de trabalho pronto.**

O objetivo é ter o caminho completo funcionando **antes** de qualquer feature — os problemas de infraestrutura (CORS, conexão com banco, build) aparecem aqui, isolados, em vez de misturados com a primeira feature.

- Projeto Spring Boot criado ([start.spring.io](https://start.spring.io)), rodando e respondendo em um endpoint de saúde (ex.: `GET /health` → `{ "status": "ok" }`).
- PostgreSQL rodando localmente (Docker é o caminho recomendado — um `compose.yaml` no repo) e o Spring conectado a ele.
- Migração de banco funcionando (Flyway ou Liquibase — sua escolha) com uma primeira migração qualquer, só para provar o mecanismo.
- Projeto React + Vite criado, com uma tela que **chama o `/health` da API e mostra a resposta**. Aqui aparece o CORS (ou o proxy de dev do Vite) — resolver isso agora, com calma.
- Um teste de exemplo rodando em cada lado (`./mvnw test` ou equivalente, e o runner do front), para o hábito existir desde o dia 1.
- README do repo com o passo a passo de subir tudo do zero — se você esquecer como roda daqui a um mês, o README falhou.

Quando a tela do front mostrar o "ok" que veio do banco de trás pra frente, o M0 acabou. É pouco visível e é normal demorar mais do que parece — é o milestone com mais coisa nova ao mesmo tempo.

## M1 — Conta, Espaço pessoal e Notas
**Resultado: dá para se cadastrar, logar e escrever notas no seu Espaço pessoal.**

- Cadastro, login, logout (sessão via cookie — ADR-004).
- Espaço pessoal criado automaticamente no cadastro (ADR-001).
- `EmailService` com implementação console (ADR-005) + e-mail de boas-vindas.
- Recuperação de senha (fluxo completo, e-mail no console).
- App do tipo **Notas**: criar o App, CRUD de notas.
- Esqueleto do `can` já existe (mesmo só com o Dono do Espaço pessoal) — para ninguém escrever checagem de permissão fora dele.

## M2 — Espaços compartilhados, roles e convite in-platform
**Resultado: dá para criar um Espaço, convidar alguém de dentro do app e trabalhar junto.**

- CRUD de Espaços compartilhados.
- `can` completo com as três roles e a tabela de permissões do domínio.
- Convite **in-platform** (aceitar/recusar), listagem de membros, remover membro, sair do Espaço.
- Regras do Dono: transferir posse, não poder sair sem transferir.

## M3 — Convite por e-mail e magic link
**Resultado: dá para trazer gente que ainda nem tem conta.**

- Token de convite (hash, expiração — ADR-003).
- Envio por e-mail com provedor real (Brevo ou Resend) substituindo o console em produção.
- Magic link copiável.
- Fluxo "aceitar convite sem estar cadastrado": cadastro que preserva o token e conclui o aceite.
- Revogação de convites pendentes.

## M4 — Tarefas e Lista de Compras
**Resultado: os Apps de uso diário em grupo.**

- App **Tarefas**: CRUD, status, prazo.
- App **Lista de Compras**: CRUD de itens, marcar/desmarcar.
- São dois clones estruturais do padrão criado em M1 com Notas — é aqui que se colhe o valor de ter feito o M1 direito.

## M5 — Financeiro
**Resultado: controle de entradas e saídas por Espaço.**

- App **Financeiro**: lançamentos (entrada/saída, valor, categoria, data), saldo e total por categoria/mês.
- Fica por último porque valores monetários e agregações têm mais armadilhas (use tipo decimal, nunca float).

## M6+ — Depois do MVP
Sem compromisso de ordem:

- Landing page (com a rotação Canteiro → Jardim → Espaço em "Seu ___ particular ou compartilhado, você decide").
- Clientes mobile (KMP + Compose Multiplatform) e desktop (Wails) sobre a mesma API.
- Redis para além de sessão (cache, filas), se doer de verdade (ADR-007).
- RBAC granular, se o gatilho do ADR-002 disparar.

## Como trabalhar (para o fluxo de mentoria)

- Um milestone por vez; dentro dele, PRs pequenos e focados.
- PR de feature descreve **qual regra do domínio** implementa e traz teste da regra central.
- Convenções de PR:
  - Uma feature (ou fatia dela) por PR — se a descrição precisa da palavra "e", provavelmente são dois PRs.
  - Trabalhe em branch; `main` só recebe código via PR revisado.
  - Antes de abrir: build passando e testes rodando localmente.
  - Na descrição: o que faz, qual regra/endpoint dos docs cobre, e como testar na mão (passos ou prints).
  - Comentário de review não é crítica pessoal — é onde o aprendizado acontece; responder "por que assim?" vale mais do que só aceitar a sugestão.
- Dúvida de arquitetura → discutir antes de codar; a resposta que mudar decisão vira ADR novo em [adrs/](adrs/).
- Os docs mandam no **o quê**; o **como** (pastas, libs, estilo) é liberdade — e responsabilidade — de quem implementa.
