# README #
 ctb-wkr-contavirtualest
 Worker relacionado a notificação de conta virtual.
 
### What is this repository for? ###

Dados relacionados ao worker:

tópico origem [governado]: topic-conf-contavirtual-estorno

tópico destino [ interno time financeiro]: topic-req-contabil-transacao

avro origem: http://schema_registry:8081/subjects/topic-conf-contavirtual-estorno-value/versions

avro destino: http://schema_registry:8081/subjects/topic-req-contabil-transacao-value/versions

### How do I get set up? ###

* Dependencies
ctb-wkr-nottransac:dev-latest

### Who do I talk to? ###
Equipe Domínio Financeiro MacEdu