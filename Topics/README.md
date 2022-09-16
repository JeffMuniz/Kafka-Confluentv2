# Automaio - Promoio de Tipicos

## Como funciona?

As informaies necessirias para criaio dos tipicos estio subdivididas em 3 pastas. Cada pasta representa um ambiente.

Dentro das pastas dos ambientes temos 2 tipos de arquivos, sendo:

1- promotion_version.properties: especifica qual a versio dos arquivos de config de tipicos para promover. Todos os arquivos usam o padrio de nome com [versio]-[nome].properties

2- [versio]-[nome].properties: Sio os arquivos com as configuraies utilizadas para criaio dos tipicos.

## Preciso subir um tipico novo! E agora?

Crie o arquivo .properties do tipico para os ambientes incrementando a iltima versio no nome do arquivo e no arquivo promotion_version.properties. Execute a pipeline!

## Como usar?

1- Copiar o conteido da branch master para o servidor Confluent onde os dockers estio executando

2- Na pasta onde o conteido foi copiado, executar o comando sh -x ./migrate-all-topics.sh [qa/stg/prod] [path para kafka-topics.sh eg./usr/bin]