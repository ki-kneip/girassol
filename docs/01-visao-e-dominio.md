# Visão e Domínio

Este documento define os conceitos e as regras de negócio do Girassol. Ele é a fonte da verdade sobre **o que** o sistema faz. Como implementar é decisão de quem implementa.

## Conceitos

### Usuário
Pessoa com conta no sistema. Identificada por e-mail único. Ao se cadastrar, recebe automaticamente seu **Espaço pessoal**.

### Espaço
Unidade de organização e compartilhamento. Tudo no Girassol vive dentro de um Espaço.

- **Espaço pessoal**: criado automaticamente no cadastro, um por usuário. Não aceita convites nem outros membros — é sempre e somente do dono. Não pode ser excluído (só junto com a conta).
- **Espaço compartilhado**: criado pelo usuário quando quiser ("Família", "República", "Trabalho"). Aceita convites e múltiplos membros.

> Modelagem: os dois são a **mesma entidade** com uma flag `pessoal`. A flag só bloqueia convites e exclusão. Não crie dois conceitos.

### Membership
Vínculo entre um Usuário e um Espaço, carregando a **role** dele naquele Espaço. Um usuário tem uma role por Espaço (pode ser Dono de um e Membro de outro).

### App
Instância de uma ferramenta dentro de um Espaço. Tipos no MVP:

| Tipo | O que faz |
|---|---|
| **Notas** | Notas de texto livre (título + corpo em markdown) |
| **Tarefas** | Lista de tarefas com título, descrição, status (pendente/concluída), prazo opcional |
| **Lista de Compras** | Itens com nome, quantidade e marcado/desmarcado |
| **Financeiro** | Lançamentos de entrada/saída com valor, descrição, categoria e data |

Regras:
- Um Espaço pode ter **quantos Apps quiser, inclusive vários do mesmo tipo** ("Notas de Estudo" e "Notas de Receitas" no mesmo Espaço).
- Todo App tem um nome dado pelo usuário.
- O conteúdo de um App é visível a todos os membros do Espaço.

### Convite
Pedido para uma pessoa entrar em um Espaço compartilhado. Detalhado em [Arquitetura](02-arquitetura.md#convites). Três formas de entrega, um modelo só:

1. **In-platform**: convite endereçado a um usuário existente, que vê e aceita/recusa dentro do app.
2. **E-mail**: envia um e-mail com magic link para qualquer endereço.
3. **Magic link**: gera uma URL que o criador compartilha como quiser (WhatsApp etc.).

## Roles e regras de permissão

Três roles fixas por enquanto (ver [ADR-002](adrs/ADR-002-roles-fixas-via-can.md)):

| Ação | Membro | Admin | Dono |
|---|:-:|:-:|:-:|
| Ver e usar o conteúdo dos Apps (criar/editar/excluir notas, tarefas, itens, lançamentos) | ✅ | ✅ | ✅ |
| Criar Apps | ✅ | ✅ | ✅ |
| Renomear/excluir Apps | ❌ | ✅ | ✅ |
| Convidar pessoas | ❌ | ✅ | ✅ |
| Remover membros / revogar convites | ❌ | ✅ | ✅ |
| Alterar role de um membro | ❌ | ❌ | ✅ |
| Renomear o Espaço | ❌ | ✅ | ✅ |
| Excluir o Espaço / transferir posse | ❌ | ❌ | ✅ |

Regras adicionais:
- Todo Espaço tem **exatamente um Dono** (quem criou). Posse pode ser transferida para outro membro.
- O Dono não pode sair do Espaço sem antes transferir a posse ou excluir o Espaço.
- Admin não pode remover outro Admin nem o Dono.
- Qualquer membro (exceto o Dono) pode **sair** do Espaço por conta própria.
- No Espaço pessoal o usuário é Dono e não existem outras roles em jogo.

## Fora de escopo do MVP

Anotado para ninguém implementar sem querer:

- Permissões por App ou por item (RBAC granular) — ver ADR-002.
- Notificações push / e-mails além dos transacionais.
- Tempo real (edição colaborativa simultânea).
- Apps mobile e desktop — a API deve permitir, mas os clientes vêm depois.
