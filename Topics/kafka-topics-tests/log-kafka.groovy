  topic-conn-conf-contavirtual                           
  topic-conn-conf-contavirtual-dlq                       
  topic-conn-conf-contavirtual-estorno                   
  topic-conn-conf-contavirtual-estorno-dlq               
  topic-conn-conf-contavirtual-estorno-retry   



  TASK-533

[Financeiro] Criar tópicos e avros do conta virtual estorno
FEITO


qorker ok
converte ok e log ok




Search for schema fields, schema subjects, topics, connectors and more...
Topic
topic-conf-contavirtual-estorno


topic-conf-contavirtual


topic-conf-agendamento-pedido


topic-grupo-enriquecimento


topic-autorizacao-transacao


topic-agendamento-pagamento


topic-conciliacao-autorizacao


topic-bi-rh-pedido


topic-bi-rh-pedido-conciliacao


See all (0) results with

LEARN


ENVIRONMENTS
DEFAULT
CCE-PROD
Cluster overview
Topics
Data integration
Stream lineage
ksqlDB
Schema Registry
CLI and tools
Support
Topics
contavirtual
Hide internal topics

Add topic
Topic name
topic-conf-contavirtual
topic-conf-contavirtual-dlq
topic-conf-contavirtual-retry


topic-conf-contavirtual-estorno
topic-conf-contavirtual-estorno-dlq
topic-conf-contavirtual-estorno-retry

topic-conn-conf-contavirtual






Partitions
Production (last min)
Consumption (last min)
Schema
4
0B/s
0B/s
4
--
--
4
0B/s
0B/s
4
--
--
4
--
--
4
--
--
4
--
--

CLOUD SUPPORT
Support portal
Support plans
Knowledge base

ADMINISTRATION
Environments
Accounts & access
Billing & payment
Cloud API keys
Audit log
Connect log events
Single sign-on
RESOURCES
Documentation
Community
Contact us
Send feedback
Settings
Sign out




então o esquema é assim

fluxo kafka
conector --> topico1 --> converter --> topico2

marca_de_verificação_branca
olhos
mãos_para_cima





13h39
mas não esta chegando nada no topico1