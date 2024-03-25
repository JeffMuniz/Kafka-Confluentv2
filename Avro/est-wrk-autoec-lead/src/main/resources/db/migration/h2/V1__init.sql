-- *********************************************************************************
-- *********************************************************************************
-- SQUAD: RH
-- JIRA: RH-7
-- DESCRIÇÃO: CRIAR TABELA DE HISTORICOS PARA ARMAZENAR INFORMAÇÕES DE ATIVAÇÃO E
--            INATIVAÇÕES DE FUNCIONARIOS
-- AUTOR: MARCELO DA CRUZ SALVADOR
-- *********************************************************************************
-- *********************************************************************************

CREATE TABLE [dbo].[HISTORICOATIVACAOINATIVACAO] (
     ID_HISTORICO   BIGINT PRIMARY KEY NOT NULL,
     ACAO           VARCHAR(20),
     CNPJ           VARCHAR(14),
     CPF            VARCHAR(11),
     DATA_HORA      DATETIME,
     NOME_OPERADOR  VARCHAR(150),
     LOGIN_OPERADOR VARCHAR(20),
     MOTIVO         VARCHAR(50),
     ID_FUNCIONARIO INT
);

ALTER TABLE HISTORICOATIVACAOINATIVACAO
    ADD FOREIGN KEY (ID_FUNCIONARIO) REFERENCES FUNCIONARIOS(ID_FUNCIONARIO);