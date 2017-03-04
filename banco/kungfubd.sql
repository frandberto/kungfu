-- Criação das Tabelas

CREATE TABLE public.evento
(
   id_evento integer, 
   codigo character varying(3), 
   descricao character varying(150), 
   pontuacao integer, 
   CONSTRAINT pk_id_evento PRIMARY KEY (id_evento)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE public.evento
  OWNER TO kungfu;
COMMENT ON COLUMN public.evento.id_evento IS 'Identificador do evento';
COMMENT ON COLUMN public.evento.descricao IS 'Descrição do evento';
COMMENT ON COLUMN public.evento.pontuacao IS 'Pontuação atribuída ao evento';
COMMENT ON TABLE public.evento
  IS 'tabela que relaciona os tipos de evento pontuados na gameficação';

CREATE TABLE public.periodo
(
  id_periodo integer NOT NULL, -- Identificador dos períodos de pontuação
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





