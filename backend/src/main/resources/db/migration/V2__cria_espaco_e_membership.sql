create table espaco (
    id        uuid primary key default gen_random_uuid(),
    nome      varchar(120) not null,
    pessoal   boolean      not null default false,
    criado_em timestamptz  not null default now()
);

create table membership (
    id         uuid primary key default gen_random_uuid(),
    usuario_id uuid        not null references usuario (id) on delete cascade,
    espaco_id  uuid        not null references espaco (id) on delete cascade,
    -- varchar + check em vez de enum nativo: evoluir enum no Postgres é doloroso
    role       varchar(10) not null check (role in ('DONO', 'ADMIN', 'MEMBRO')),
    criado_em  timestamptz not null default now(),
    unique (usuario_id, espaco_id)
);

-- Invariante do domínio: exatamente um DONO por espaço (doc 01)
create unique index membership_um_dono_por_espaco on membership (espaco_id) where role = 'DONO';

-- Lookup mais comum: "espaços deste usuário"
create index membership_usuario_idx on membership (usuario_id);
