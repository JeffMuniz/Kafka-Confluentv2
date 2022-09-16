Cards - Instrucoes

Tarefas-1
[Financeiro] Criar tópicos e avros do conta virtual estorno
Descrição
Criar tópicos e avros do conta virtual estorno

1 - Criar tópicos:

Versão: v1.0.2

Governados:

https://bitbucket.org/machina/kafka-topics-config/src/v1.0.2/prod/v1-topic-conf-contavirtual-estorno.properties

https://bitbucket.org/machina/kafka-topics-config/src/v1.0.2/prod/v1-topic-conf-contavirtual-estorno-dlq.properties

https://bitbucket.org/machina/kafka-topics-config/src/v1.0.2/prod/v1-topic-conf-contavirtual-estorno-retry.properties

Não Governados:
https://bitbucket.org/machina/kafka-topics-config/src/v1.0.2/prod/v1-topic-conn-conf-contavirtual-estorno.properties
https://bitbucket.org/machina/kafka-topics-config/src/v1.0.2/prod/v1-topic-conn-conf-contavirtual-estorno-dlq.properties
https://bitbucket.org/machina/kafka-topics-config/src/v1.0.2/prod/v1-topic-conn-conf-contavirtual-estorno-retry.properties

 

2 - Criar Avros
https://bitbucket.org/machina/conn-contavirtual-estorno-avro/commits/tag/v1.0.0
https://bitbucket.org/machina/contavirtual-estorno-avro/commits/tag/v1.0.0




...............................................................................................................................................................................................................................................

Tarefas-2
[Financeiro] Deploy dos conectores do conta virtual
Fazer deploy e alterações dos conectores do conta virtual:

1 - Remover conector conn-in-contavirtual

2 - Criar novo conector contavirtual-pedido
https://bitbucket.org/machina/cnn-cnf-financa/src/v1.1.3/conn-in-contavirtual-pedido.json


3 - Criar novo conector contavirtual-utilizada
https://bitbucket.org/machina/cnn-cnf-financa/src/v1.1.3/conn-in-contavirtual-utilizada.json


4 - Criar conector conn-in-contavirtual-estorno
https://bitbucket.org/machina/cnn-cnf-financa/src/v1.1.3/conn-in-contavirtual-estorno.json


curl -X GET http://localhost:8083/connectors/conn-in-contavirtual-estorno | jq
curl -X GET http://localhost:8083/connectors/conn-in-contavirtual-utilizada | jq
curl -X GET http://localhost:8083/connectors/conn-in-contavirtual-pedido | jq

curl -X GET http://localhost:8083/connectors/conn-in-contavirtual-pedido/status | jq
curl -X GET http://localhost:8083/connectors/conn-in-contavirtual-utilizada/status | jq
curl -X GET http://localhost:8083/connectors/conn-in-contavirtual-pedido/status | jq


 


  "conn-in-contavirtual-estorno",
  "conn-in-contavirtual-pedido",
  "conn-in-contavirtual-utilizada",

  2 - Criar novo conector contavirtual-pedidohttps://bitbucket.org/machina-cartao/cnn-cnf-financa/src/v1.1.3/conn-in-contavirtual-pedido.json

3 - Criar novo conector contavirtual-utilizada

https://bitbucket.org/machina-cartao/cnn-cnf-financa/src/v1.1.3/conn-in-contavirtual-utilizada.json

4 - Criar conector conn-in-contavirtual-estornohttps://bitbucket.org/machina-cartao/cnn-cnf-financa/src/v1.1.3/conn-in-contavirtual-estorno.json


...............................................................................................................................................................................................................................................

Tarefas-3

Fazer deploy dos worker no namespace financeiro-contabil-prd do Rancher dentro do projeto Financeiro-Contabil

1 - Implantar nova versão do ctb-wkr-contavirtual:https://bitbucket.org/machina/ctb-wkr-contavirtual/src/v1.0.1/

2 - Criar novo worker do ctb-wkr-contavirtualesthttps://bitbucket.org/machina/ctb-wkr-contavirtualest/commits/tag/v1.0.0

Variáveis de ambiente:SERVER_PORT = 8080LOGGING_LEVEL_ROOT = INFOLOGGING_BR_COM_machina = INFOColocar as variaveis abaixo e utilizar a chaves do configMap:SPRING_KAFKA_BOOTSTRAP_SERVERSSPRING_KAFKA_CONSUMER_PROPERTIES_SCHEMA_REGISTRY_URLSPRING_KAFKA_PRODUCER_PROPERTIES_SCHEMA_REGISTRY_URLSPRING_KAFKA_PROPERTIES_SCHEMA_REGISTRY_URLColocar as variaveis abaixo e utilizar as chaves do secret:machina_KAFKA_CLOUD_passingthroughportalwordmachina_KAFKA_CLOUD_SCHEMA-REGISTRY_KEYmachina_KAFKA_CLOUD_SCHEMA-REGISTRY_SECRETmachina_KAFKA_CLOUD_USERNAME

 



Tarefas-4
[Financeiro] Fazer deploy dos converters do conta virtual
No projeto Converter e no namespace converter-prd do Rancher:

1 - Fazer deploy nova versão converter conv-contavirtual:

 https://bitbucket.org/machina/avro-contavirtual-converter/commits/tag/v1.0.2

 

2 - Criar conta virtual estorno converter
Criar serviço: conv-contavirtual-estorno:
Versão: https://bitbucket.org/machina/avro-contavirtual-estorno-converter/commits/tag/v1.0.0

Variáveis de ambiente:
SERVER_PORT = 8080
LOGGING_LEVEL_ROOT = INFO
LOGGING_BR_COM_machina = INFO
SPRING_PRODUCER_OUTBOUND-SCHEMA-ID = 101010 (
    topic-conf-contavirtual-estorno-value
Type: Avro
Compatibility mode: Backward
Used by topic: topic-conf-contavirtual-estorno
Version 1 (current)
Schema ID: 101010
)

Colocar as vairiaveis abaixo e utilizar a chaves do configMap:
SPRING_KAFKA_BOOTSTRAP_SERVERS
SPRING_KAFKA_CONSUMER_PROPERTIES_SCHEMA_REGISTRY_URL
SPRING_KAFKA_PRODUCER_PROPERTIES_SCHEMA_REGISTRY_URL
SPRING_KAFKA_PROPERTIES_SCHEMA_REGISTRY_URL
Colocar as variaveis abaixo e utilizar as chaves do secret:
machina_KAFKA_CLOUD_passingthroughportalword
machina_KAFKA_CLOUD_SCHEMA-REGISTRY_KEY
machina_KAFKA_CLOUD_SCHEMA-REGISTRY_SECRET
machina_KAFKA_CLOUD_USERNAME


