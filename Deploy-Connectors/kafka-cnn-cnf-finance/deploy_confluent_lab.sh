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

# SOBE A VERSÃO DO ARQUIVO E GUARDA EM UM TEMPORARIO
if [ ''${CONTINGENCY} = 'NO' ];
then
    OIFS=$IFS
    IFS=' '
    mails2=$files
    for x in $mails2
    do
            sed -i '/version/c\           "version" : '${BUILD_NUMBER}',' $x
            cp $x $x'_TEMP'
    
    done
fi

# ABRE OS ARQUIVOS DOS CONECTORES E ALTERA AS VARIAVEIS DE ACORDO COM O AMBIENTE
sed -i 's/'{{ENV_DB_HOST}}'/'${LAB_DB_HOST}'/g' $files
sed -i 's/'{{ENV_DB_PORT}}'/'${LAB_DB_PORT}'/g' $files
sed -i 's/'{{ENV_DB_BLUE_NAME}}'/'${LAB_DB_BLUE_NAME}'/g' $files
sed -i 's/'{{ENV_DB_ADQ_NAME}}'/'${LAB_DB_ADQ_NAME}'/g' $files
sed -i 's/'{{ENV_DB_USER}}'/'${LAB_DB_USER}'/g' $files
sed -i 's/'{{ENV_DB_passingthroughportalword}}'/'${LAB_DB_passingthroughportalword}'/g' $files
sed -i 's/'{{ENV_API_SCHEMA_REGISTRY_URL}}'/'${LAB_API_SCHEMA_REGISTRY_URL}'/g' $files
sed -i 's/'{{ENV_ELASSANDRA}}'/'${LAB_ELASSANDRA}'/g' $files
sed -i 's/'{{ENV_KEYSPACE_BI}}'/'${LAB_KEYSPACE_BI}'/g' $files


# PASSA ARQUIVO CONECTOR POR CONECTOR APLICANDO A ACAO DE INCLUSAO/EXCLUSAO/ALTERACAO
OIFS=$IFS
IFS=' '
mails2=$files
for x in $mails2
do

    # INCLUI O CONECTOR
    if [ ''${ACTION} = 'ADD' ];
    then 
      	url_api_save='curl -X POST http://'${LAB_API_CONNECTOR_URL}'/connectors -H "Content-Type: application/json" -d '
      	url_api_save=$url_api_save"'"
        value=$(<''$x)
        url_api_save=$url_api_save''$value"'"
        echo "$url_api_save"
        eval "$url_api_save"
    fi
	
    # EXCLUI O CONECTOR
    if [ ''${ACTION} = 'DELETE' ];
    then 
    	x=${x//'.json'/''}
      	url_api_delete='curl -X DELETE http://'${LAB_API_CONNECTOR_URL}'/connectors/'$x' -H "Content-Type: application/json"'
        echo "$url_api_delete"
        eval $url_api_delete
    fi
	
    # EXCLUI E INCLUI O CONECTOR
    if [ ''${ACTION} = 'UPDATE' ];
    then 
    	x=${x//'.json'/''}
      	url_api_delete='curl -X DELETE http://'${LAB_API_CONNECTOR_URL}'/connectors/'$x' -H "Content-Type: application/json"'
        echo "$url_api_delete"
        eval $url_api_delete
		
        sleep 10
        
		url_api_save='curl -X POST http://'${LAB_API_CONNECTOR_URL}'/connectors -H "Content-Type: application/json" -d '
      	url_api_save=$url_api_save"'"
        value=$(<''$x)
        url_api_save=$url_api_save''$value"'"
        echo "$url_api_save"
        eval "$url_api_save"
        
    fi
	sleep 5
done

#REALIZA O COMMIT DAS NOVAS VERSOES
git config --global user.email "jenkins@maquiinaedu.com.br"
git config --global user.name "Jenkins MacVisaCard Card"
git config --global --add remote.origin.proxy ""
if [ ''${CONTINGENCY} = 'NO' ];
then
    rm -rf *.json
    for file in *_TEMP
    do
      mv "$file" "${file/'_TEMP'/}"
    done
	
	git status
	git ls-files --modified | xargs git add
    git commit -m 'Ajuste de versao para '${BUILD_NUMBER}'.' 
    git push origin develop
fi
