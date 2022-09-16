set -e

# FORMATA O NOME DOS CONECTORS COM .JSON
if [ ''${CONNECTORS} = 'ALL' ];
then 
  files="*.json"
else
  files=${CONNECTORS//';'/'.json '}
  files=$files'.json'
fi
echo "$files"


# ABRE OS ARQUIVOS DOS CONECTORES E ALTERA AS VARIAVEIS DE ACORDO COM O AMBIENTE
sed -i 's/'{{ENV_DB_HOST}}'/'${STG_CCLOUD_DB_HOST}'/g' $files
sed -i 's/'{{ENV_DB_PORT}}'/'${STG_CCLOUD_DB_PORT}'/g' $files
sed -i 's/'{{ENV_DB_NAME}}'/'${STG_CCLOUD_DB_NAME}'/g' $files
sed -i 's/'{{ENV_DB_BLUE_NAME}}'/'${STG_CCLOUD_DB_BLUE_NAME}'/g' $files
sed -i 's/'{{ENV_DB_ADQ_NAME}}'/'${STG_CCLOUD_DB_ADQ_NAME}'/g' $files
sed -i 's/'{{ENV_DB_USER}}'/'${STG_CCLOUD_DB_USER}'/g' $files
sed -i 's/'{{ENV_DB_passingthroughportalword}}'/'${STG_CCLOUD_DB_passingthroughportalword}'/g' $files
sed -i 's/'{{ENV_API_SCHEMA_REGISTRY_URL}}'/'${STG_CCLOUD_API_SCHEMA_REGISTRY_URL}'/g' $files
sed -i 's/'{{ENV_ELASSANDRA}}'/'${STG_CCLOUD_ELASSANDRA}'/g' $files
sed -i 's/'{{ENV_KEYSPACE_BI}}'/'${STG_CCLOUD_KEYSPACE_BI}'/g' $files
sed -i 's/'{{ENV_DB_CAN_ADQ_NAME}}'/'${STG_CCLOUD_DB_CAN_ADQ_NAME}'/g' $files
sed -i 's/'{{ENV_DB_ADQ_EXTRATO_ELETRONICO}}'/'${STG_CCLOUD_DB_ADQ_EXTRATO_ELETRONICO}'/g' $files
sed -i 's/'{{ENV_DB_BLUE_EXTRATO_ELETRONICO}}'/'${STG_CCLOUD_DB_BLUE_EXTRATO_ELETRONICO}'/g' $files
sed -i 's/'{{ENV_API_SCHEMA_REGISTRY_KEY}}'/'${STG_CCLOUD_API_SCHEMA_REGISTRY_KEY}'/g' $files
sed -i 's/{{ENV_API_SCHEMA_REGISTRY_SECRET}} *./WPm7UtdD1pQZmGBs6Pf0oKrX4taJmiMDPh7uUV8KJbD0rDWkX\/jp24qArHNF4T1V"/g' $files


# PASSA ARQUIVO CONECTOR POR CONECTOR APLICANDO A ACAO DE INCLUSAO/EXCLUSAO/ALTERACAO
OIFS=$IFS
IFS=' '
mails2=$files

for x in $mails2
do

    # INCLUI O CONECTOR
    if [ ''${ACTION} = 'ADD' ];
    then 
      	#url_api_save='curl -H "Content-Type: application/json" -X POST -d @'$mails2' http://'${STG_CCLOUD_API_CONNECTOR_URL}'/connectors | jq'
        url_api_save='curl -H "Content-Type: application/json" -X POST -d @'$mails2' http://'${STG_CCLOUD_API_CONNECTOR_URL}'/connectors'
        echo "$url_api_save"
        eval "$url_api_save"
    fi
	
    # EXCLUI O CONECTOR
    if [ ''${ACTION} = 'DELETE' ];
    then 
    	x=${x//'.json'/''}
      	url_api_delete='curl -X DELETE http://'${STG_CCLOUD_API_CONNECTOR_URL}'/connectors/'$x' -H "Content-Type: application/json"'
        echo "$url_api_delete"
        eval $url_api_delete
    fi
	
    # EXCLUI E INCLUI O CONECTOR
    if [ ''${ACTION} = 'UPDATE' ];
    then 
    	x=${x//'.json'/''}
      	url_api_delete='curl -X DELETE http://'${STG_CCLOUD_API_CONNECTOR_URL}'/connectors/'$x' -H "Content-Type: application/json"'
        echo "$url_api_delete"
        eval $url_api_delete
        
		sleep 10
		
      	# url_api_save='curl -H "Content-Type: application/json" -X POST -d @'$mails2' http://'${STG_CCLOUD_API_CONNECTOR_URL}'/connectors | jq'
        url_api_save='curl -H "Content-Type: application/json" -X POST -d @'$mails2' http://'${STG_CCLOUD_API_CONNECTOR_URL}'/connectors'
        echo "$url_api_save"
        eval "$url_api_save"
        
    fi
	sleep 5
done
