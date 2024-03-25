package br.com.mac.funcionario.utils;

public interface Constantes {

  // tags
  String TAG_FUNCIONARIO = "Funcionario";
  String TAG_HISTORICO = "Historico";

  // paths
  String PATH_FUNCIONARIO = "/";
  String PATH_CONSULTAR_FUNCIONARIO = "/consultar";
  String PATH_QUANTIDADE_FUNCIONARIO = "/quantidade-funcionarios";
  String PATH_FUNCIONARIO_ATIVAR = "/ativar";
  String PATH_FUNCIONARIO_INATIVAR = "/inativar";
  String PATH_HISTORICO = "/historico";
  String PATH_FUNCIONARIO_CONSULTAR_STATUS = "/consultar-status-funcionario";
  String PATH_HISTORICO_CPF = "/historico-cpf";
  String PATH_HISTORICO_CNPJ = "/historico-cnpj";
  String PATH_USUARIO = "/usuario";

  // values/notes controllers
  String RESOURCE_ATIVAR_FUNCIONARIO_VALUE = "Ativar funcionario";
  String RESOURCE_INATIVAR_FUNCIONARIO_VALUE = "Inativar funcionario";
  String RESOURCE_CONSULTAR_STATUS_FUNCIONARIO_VALUE = "Consultar status dos funcionarios";
  String RESOURCE_LISTAR_FUNCIONARIOS_VALUE = "Listar funcionarios";
  String RESOURCE_LISTAR_FUNCIONARIOS_QUANTIDADE_VALUE = "Quantidade de funcionarios";
  String RESOURCE_HISTORICO_CONSULTAR_POR_CPF_VALUE = "Consultar histórico por cpf";
  String RESOURCE_HISTORICO_CONSULTAR_POR_CNPJ_VALUE = "Consultar histórico por cnpj";

  String RESOURCE_ATIVAR_FUNCIONARIO_NOTES = "Ativa o funcionário para receber macefício";
  String RESOURCE_INATIVAR_FUNCIONARIO_NOTES = "Inativa o funcionário para não receber macefício";
  String RESOURCE_CONSULTAR_STATUS_FUNCIONARIO_NOTES = "Consultar status dos funcionarios";
  String RESOURCE_LISTAR_FUNCIONARIOS_NOTES = "Listar funcionarios";
  String RESOURCE_LISTAR_FUNCIONARIOS_QUANTIDADE_NOTES = "Quantidade de funcionarios";
  String RESOURCE_HISTORICO_CONSULTAR_POR_CPF_NOTES = "Consultar histórico de um vínculo por cpf";
  String RESOURCE_HISTORICO_CONSULTAR_POR_CNPJ_NOTES = "Consultar histórico de um vínculo por cnpj";

}
