alter table conteudo
  add column slug           varchar(160),
  add column resumo         varchar(300),
  add column minutos        smallint,
  add column atualizado_por bigint references admin_user (id);

update conteudo set slug = 'conteudo-' || id;

alter table conteudo
  alter column slug set not null,
  add constraint uk_conteudo_slug unique (slug);

create table pagina (
  chave          varchar(60) primary key,
  titulo         varchar(200),
  corpo          text        not null,
  atualizado_em  timestamptz not null default now(),
  atualizado_por bigint references admin_user (id)
);

insert into pagina (chave, titulo, corpo) values
  ('inicio',      'Como você está hoje?', 'Registre seu check-in, leia conteúdo da liga ou encontre caminhos de ajuda.'),
  ('checkin',     'Check-in emocional',   ''),
  ('conteudos',   'Conteúdos',            ''),
  ('ajuda',       'Preciso de ajuda',     ''),
  ('autocuidado', 'Autocuidado',          ''),
  ('lasm',        'A LASM',               ''),
  ('rodape',      null,                   '');
