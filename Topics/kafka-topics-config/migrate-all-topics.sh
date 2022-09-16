#!/bin/bash

echo "Usage -- migrate-all-topics.sh [qa|dev|prod] [kafka-topics.sh dir]"

ENV=${1}
KAFKA_SCRIPTS_DIR=${2}
CONFLUENT_HOST=${3}
PROMOTION_VERSION="$(cat ${ENV}/promotion_version.properties | grep version | cut -d'=' -f2)"


for propfile in $(ls ${ENV} | grep "${PROMOTION_VERSION}-"); do
    sh -x ./migrate-topic.sh ${ENV} "${propfile}" ${KAFKA_SCRIPTS_DIR} ${CONFLUENT_HOST}
done;

exit 0
