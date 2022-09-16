#!/bin/bash

files=""
ACTION=@option.action@
CONNECTORS=@option.connectors@
USER=@option.user@


PRD_DB_HOST=10.1.1.1
PRD_DB_PORT=1433
PRD_DB_BLUE_NAME=Blue
PRD_DB_ADQ_NAME=Adquirente
PRD_DB_USER=user_machina_confluent
PRD_DB_passingthroughportalword=@option.confluent_passingthroughportalword@
PRD_API_SCHEMA_REGISTRY_URL=schema-registry:8081
PRD_API_CONNECTOR_URL=10.50.131.86:8083
PRD_ELASSANDRA=10.50.131.88
PRD_KEYSPACE_BI=informacional

formatNomeArquivo(){
  echo "formatNomeArquivo()"
  if [ ''${CONNECTORS} = 'ALL' ];
  then
    files="*.json"
  else
    files=${CONNECTORS//';'/'.json '}
    files=$files'.json'
  fi
}

modificarSecrets(){
  echo "modificarSecrets()"
  sed -i 's/'{{ENV_DB_HOST}}'/'${PRD_DB_HOST}'/g' $files && sed -i 's/'{{ENV_DB_PORT}}'/'${PRD_DB_PORT}'/g' $files && \
  sed -i 's/'{{ENV_DB_NAME}}'/'${PRD_DB_NAME}'/g' $files && sed -i 's/'{{ENV_DB_BLUE_NAME}}'/'${PRD_DB_BLUE_NAME}'/g' $files && \
  sed -i 's/'{{ENV_DB_ADQ_NAME}}'/'${PRD_DB_ADQ_NAME}'/g' $files && sed -i 's/'confluent_machina_login'/'${PRD_DB_USER}'/g' $files && \
  sed -i 's/'machina-passwd'/'${PRD_DB_passingthroughportalword}'/g' $files && sed -i 's/'https://machina-aws-clusters.us-east-2.aws.confluent.cloud'/'${PRD_API_SCHEMA_REGISTRY_URL}'/g' $files && \
  sed -i 's/'{{ENV_ELASSANDRA}}'/'${PRD_ELASSANDRA}'/g' $files && sed -i 's/'{{ENV_KEYSPACE_BI}}'/'${PRD_KEYSPACE_BI}'/g' $files 
  
}

convertJsonProperties(){
    echo "convertJsonProperties()"
    sudo rm -rfv temp/* && sudo mkdir -p temp && sudo chown $USER:$USER temp

    for file in ${files[@]}
    do
      tempFile="./temp/${file//.json/.properties}"

      # CONVERTE O JSON EM PROPERTIES
      echo "name="$(jq -r '.name' "$file") >> "$tempFile"
      sudo jq -r '.config | to_entries[] | "\(.key)=\(.value)"' $file >> "$tempFile" 
    done

}

enviarConectores(){
  echo "enviarConectores()"
  cd temp 
  sudo rm -rf /etc/kafka/connectors/ && sudo mkdir -p /etc/kafka/connectors/ &&  sudo chown $USER:$USER /etc/kafka/connectors/ && mv *.properties /etc/kafka/connectors/
}

getNomesConectores(){
  files=$(cd /etc/kafka/connectors/ && ls -lh *.properties | awk '{ print $9 }')
  echo $files
}

iniciarConectores(){
  connectorName=$1

  echo "connect-standalone -daemon /etc/kafka/connect-standalone.properties /etc/kafka/connectors/$connectorName >> /tmp/$connectorName &"
  #connect-standalone /etc/kafka/connect-standalone.properties /etc/kafka/connectors/$connectorName >> /tmp/$connectorName &
 
}


main()
{	
    echo "Iniciando script"

    cd confluent
    formatNomeArquivo
    modificarSecrets
    convertJsonProperties

    enviarConectores
    nomes=$(getNomesConectores)
    for nome in ${nomes[@]}
    do
      iniciarConectores $nome
    done
    
     echo "Script Finalizado"
}

######## START #########################
main