 cat ./GMUDS_ANTIGAS/DEVOPS-555/conn-in-agendamento-pedido.json
{
  "name": "conn-in-agendamento-pedido",
  "config": {
    "version" : "1",
    "description" : "Conector de entrada de dados referente a Agendamento Pedido",
    "connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector",
    "connection.url": "jdbc:sqlserver://10.50.131.85:1433;databaseName=Blue",
    "connection.user": "confluent_mac_login",
    "connection.password": "9D8?m>?q/2>$lQm",
    "topic.prefix": "topic-conn-conf-agendamento-pedido",
    "poll.interval.ms": "60000",
    "mode": "timestamp+incrementing",
    "timestamp.column.name": "dataSolicitacao",
    "timestamp.initial": "-1",
    "incrementing.column.name": "idBoleto",
    "incrementing.offset": "0",
    "validate.non.null": true,
    "query": "SELECT * FROM (SELECT be.Id_Boleto AS idBoleto, CAST(be.ValorBoleto AS NUMERIC(15,2)) AS valor, cb.DataProcessamento AS dataAgendamento, be.Data
Emissao AS dataSolicitacao FROM arquivocargastd arq (nolock) INNER JOIN Cargasmaceficios cb (nolock) ON cb.ID_ARQUIVOCARGASTD = arq.ID_ARQUIVOCARGASTD INNER J
OIN CargaControleFinanceiro cf (nolock) ON cf.Id_CargaControleFinanceiro = cb.Id_CargaControleFinanceiro INNER JOIN BoletosEmitidos be (nolock) on be.Id_Bolet
o = cf.Id_Boleto WHERE cb.status not in (6)  and ARQ.STATUS < 7 and be.DataEmissao <= DATEADD(hh, -2, GETDATE())  GROUP BY be.Id_Boleto, be.ValorBoleto, cb.Da
taProcessamento, be.DataEmissao) AS AgendamentoPedido",
    "value.converter.schema.registry.basic.auth.user.info": "Q4FE6BV5B6TUPEKI:f6BQNuTDyq6rzgzrvwWrDAZASpd0OiU8+OZ0wCiSfUdhROVkoGNcp3z3TCZsXxMP",
    "value.converter.schema.registry.url": "https://psrc-em82q.us-east-2.aws.confluent.cloud",   
    "key.converter.schema.registry.basic.auth.user.info": "Q4FE6BV5B6TUPEKI:f6BQNuTDyq6rzgzrvwWrDAZASpd0OiU8+OZ0wCiSfUdhROVkoGNcp3z3TCZsXxMP",
    "key.converter.schema.registry.url": "https://psrc-em82q.us-east-2.aws.confluent.cloud",
    "auto.register.schemas":"true",
    "transforms":"createKey,extractInt,setSchemaName",
    "transforms.createKey.type":"org.apache.kafka.connect.transforms.ValueToKey",
    "transforms.createKey.fields":"idBoleto",
    "transforms.extractInt.type":"org.apache.kafka.connect.transforms.ExtractField$Key",
    "transforms.extractInt.field":"idBoleto",
    "transforms.setSchemaName.type":"org.apache.kafka.connect.transforms.SetSchemaMetadata$Value",
    "transforms.setSchemaName.schema.name":"br.com.mac.schema.ConnConfirmacaoAgendamentoPedidoValue",
    "key.converter":"io.confluent.connect.avro.AvroConverter",
    "value.converter":"io.confluent.connect.avro.AvroConverter",
    "key.converter.basic.auth.credentials.source": "USER_INFO",
    "value.converter.basic.auth.credentials.source": "USER_INFO"