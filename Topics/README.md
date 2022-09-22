# Automação - Promoção de Tópicos

## Como funciona?

As informações necessárias para criação dos tópicos estão subdivididas em 3 pastas. Cada pasta representa um ambiente.

Dentro das pastas dos ambientes temos 2 tipos de arquivos, sendo:

1- promotion_version.properties: especifica qual a versão dos arquivos de config de tópicos para promover. Todos os arquivos usam o padrão de nome com [versão]-[nome].properties

2- [versão]-[nome].properties: São os arquivos com as configurações utilizadas para criação dos tópicos.

## Preciso subir um tópico novo! E agora?

Crie o arquivo .properties do tópico para os ambientes incrementando a última versão no nome do arquivo e no arquivo promotion_version.properties. Execute a pipeline!

## Como usar?

1- Copiar o conteúdo da branch master para o servidor Confluent onde os dockers estão executando

2- Na pasta onde o conteúdo foi copiado, executar o comando sh -x ./migrate-all-topics.sh [qa/stg/prod] [path para kafka-topics.sh eg./usr/bin]