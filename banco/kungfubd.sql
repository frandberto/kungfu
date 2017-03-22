-- Criação das Tabelas do banco

CREATE SEQUENCE public.sq_id_gamificacao
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.sq_id_gamificacao
  OWNER TO kungfu;

CREATE SEQUENCE public.sq_id_evento
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.sq_id_evento
  OWNER TO kungfu;

CREATE SEQUENCE public.sq_id_periodo
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.sq_id_periodo
  OWNER TO kungfu;

CREATE SEQUENCE public.sq_id_usuario
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.sq_id_usuario
  OWNER TO kungfu;

CREATE TABLE public.evento
(
  id_evento integer NOT NULL DEFAULT nextval('sq_id_evento'::regclass), -- Identificador do evento
  codigo character varying(3),
  descricao character varying(150), -- Descrição do evento
  pontuacao integer, -- Pontuação atribuída ao evento
  CONSTRAINT pk_id_evento PRIMARY KEY (id_evento)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.evento
  OWNER TO kungfu;
COMMENT ON TABLE public.evento
  IS 'Tabela que relaciona os tipos de evento pontuados na gameficação';
COMMENT ON COLUMN public.evento.id_evento IS 'Identificador do evento';
COMMENT ON COLUMN public.evento.descricao IS 'Descrição do evento';
COMMENT ON COLUMN public.evento.pontuacao IS 'Pontuação atribuída ao evento';

CREATE TABLE public.gamificacao
(
  id_gamificacao integer NOT NULL DEFAULT nextval('sq_id_gamificacao'::regclass), -- Identificador da gamificação
  id_evento integer NOT NULL, -- Identificador do evento que tem a participação do usuário.
  "id_período" integer NOT NULL, -- Identificador do período no qual o usuário participou do evento
  id_usuario integer NOT NULL, -- Identificação do usuário que participou do evento
  data date NOT NULL, -- Data de registro da participação no evento
  tx_observacao text, -- Texto de observação
  CONSTRAINT pk_id_gameficacao PRIMARY KEY (id_gamificacao),
  CONSTRAINT fk_id_evento FOREIGN KEY (id_evento)
      REFERENCES public.evento (id_evento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_id_periodo FOREIGN KEY ("id_período")
      REFERENCES public.periodo (id_periodo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario)
      REFERENCES public.usuario (id_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.gamificacao
  OWNER TO kungfu;
COMMENT ON TABLE public.gamificacao
  IS 'Tabela que armazena o resultado da atribuição do jogo aos participantes num período de determinado';
COMMENT ON COLUMN public.gamificacao.id_gamificacao IS 'Identificador da gamificação';
COMMENT ON COLUMN public.gamificacao.id_evento IS 'Identificador do evento que tem a participação do usuário.';
COMMENT ON COLUMN public.gamificacao."id_período" IS 'Identificador do período no qual o usuário participou do evento';
COMMENT ON COLUMN public.gamificacao.id_usuario IS 'Identificação do usuário que participou do evento';
COMMENT ON COLUMN public.gamificacao.data IS 'Data de registro da participação no evento ';
COMMENT ON COLUMN public.gamificacao.tx_observacao IS 'Texto de observação';

CREATE TABLE public.periodo
(
  id_periodo integer NOT NULL DEFAULT nextval('sq_id_periodo'::regclass), -- Identificador dos períodos de pontuação
  descricao character varying(100), -- Descrição do período
  dt_fim date, -- Data de fim do período
  dt_inicio date, -- Data de início do período
  CONSTRAINT pk_id_periodo PRIMARY KEY (id_periodo)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.periodo
  OWNER TO kungfu;
COMMENT ON TABLE public.periodo
  IS 'Tabela que identifica os períodos de pontuação';
COMMENT ON COLUMN public.periodo.id_periodo IS 'Identificador dos períodos de pontuação';
COMMENT ON COLUMN public.periodo.descricao IS 'Descrição do período';
COMMENT ON COLUMN public.periodo.dt_fim IS 'Data de fim do período';
COMMENT ON COLUMN public.periodo.dt_inicio IS 'Data de início do período';

CREATE TABLE public.usuario
(
  id_usuario integer NOT NULL DEFAULT nextval('sq_id_usuario'::regclass), -- Identificador do Usuário
  codigo character varying(11) NOT NULL, -- Código de identificação do usuário. Pode ser o código do cpf.
  nome_completo character varying(200) NOT NULL, -- Nome completo do usuário
  apelido character varying(50) NOT NULL, -- Apelido do usuário (nome de guerra)
  email character varying(200),
  CONSTRAINT pk_id_usuario PRIMARY KEY (id_usuario)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.usuario
  OWNER TO kungfu;
COMMENT ON TABLE public.usuario
  IS 'Tabela que armazena os usuários do sistema';
COMMENT ON COLUMN public.usuario.id_usuario IS 'Identificador do Usuário';
COMMENT ON COLUMN public.usuario.codigo IS 'Código de identificação do usuário. Pode ser o código do cpf.';
COMMENT ON COLUMN public.usuario.nome_completo IS 'Nome completo do usuário';
COMMENT ON COLUMN public.usuario.apelido IS 'Apelido do usuário (nome de guerra)';





