create table conteudo (
  id            bigserial primary key,
  titulo        varchar(200) not null,
  categoria     varchar(60)  not null,
  corpo         text         not null,
  status        varchar(20)  not null default 'RASCUNHO',
  publicado_em  timestamptz,
  criado_em     timestamptz  not null default now(),
  atualizado_em timestamptz  not null default now()
);

create table checkin (
  id        bigserial primary key,
  nivel     smallint    not null check (nivel between 1 and 5),
  contextos varchar(200),
  criado_em timestamptz not null default now()
);

create table admin_user (
  id           bigserial primary key,
  email        varchar(160) not null unique,
  senha_hash   varchar(100) not null,
  nome         varchar(120) not null,
  papel        varchar(20)  not null default 'EDITOR',
  ativo        boolean      not null default true,
  ultimo_login timestamptz,
  criado_em    timestamptz  not null default now()
);

create index idx_conteudo_status on conteudo (status);
create index idx_checkin_criado  on checkin (criado_em);