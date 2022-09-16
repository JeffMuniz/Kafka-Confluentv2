#!/bin/bash

echo "Usage -- migrate-topic.sh [qa|dev|prod] [topic properties file] [kafka-topics.sh dir]"

ENV=${1}
TOPIC_CONFIG_FILE=${2}
KAFKA_SCRIPTS_DIR=${3}
CONFLUENT_HOST=${4}

function promotionConfig {
    grep "$1" ${ENV}/promotion_version.properties|cut -d'=' -f2
}

function topicConfig {
    grep "$1" ${ENV}/${TOPIC_CONFIG_FILE}|cut -d'=' -f2
}

#BOOTSTRAP_SERVER=promotionConfig 'bootstrap-server'
#REPLICATION_FACTOR=topicConfig 'replication-factor'
#PARTITIONS=topicConfig 'partitions'
#TOPIC=$(topicConfig 'topic')
#RETENTION=topicConfig 'retention.ms'

#echo ${TOPIC}

${KAFKA_SCRIPTS_DIR}kafka-topics.sh --create --bootstrap-server ${CONFLUENT_HOST} --replication-factor $(topicConfig 'replication-factor') --partitions $(topicConfig 'partitions') --topic $(topicConfig 'topic') --config retention.ms=$(topicConfig 'retention.ms')
