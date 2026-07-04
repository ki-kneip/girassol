create table usuario (
    id         uuid primary key default gen_random_uuid(),
    nome       varchar(120) not null,
    email      varchar(255) not null unique,
    senha_hash varchar(100) not null,
    criado_em  timestamptz  not null default now()
);
