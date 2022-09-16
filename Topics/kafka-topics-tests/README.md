#  

## Como funciona?

As informa  es necess rias para cria  o dos t picos est o subdivididas em 3 pastas. Cada pasta representa um ambiente.

Dentro das pastas dos ambientes temos 2 tipos de arquivos, sendo:

1- promotion_version.properties: especifica qual a vers o dos arquivos de config de t picos para promover. Todos os arquivos usam o padr o de nome com [vers o]-[nome].properties

2- [vers o]-[nome].properties: S o os arquivos com as configura  es utilizadas para cria  o dos t picos.

## Preciso subir um t pico novo! E agora?

Crie o arquivo .properties do t pico para os ambientes incrementando a  ltima vers o no nome do arquivo e no arquivo promotion_version.properties. Execute a pipeline!

## Como usar?

1- Copiar o conte do da branch master para o servidor Confluent onde os dockers est o executando

2- Na pasta onde o conte do foi copiado, executar o comando sh -x ./migrate-all-topics.sh [qa/stg/prod] [path para kafka-topics.sh eg./usr/bin]