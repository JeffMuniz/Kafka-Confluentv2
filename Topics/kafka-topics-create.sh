Não Governados
topic-conn-conf-contavirtual-estorno
topic-conn-conf-contavirtual-estorno-dlq
topic-conn-conf-contavirtual-estorno-retry

ccloud kafka topic create topic-conn-conf-contavirtual-estorno --partitions 4 --cluster machi-naa
ccloud kafka topic update topic-conn-conf-contavirtual-estorno --config="retention.ms=604800000"

ccloud kafka topic create topic-conn-conf-contavirtual-estorno-dlq --partitions 4 --cluster machi-naa
ccloud kafka topic update topic-conn-conf-contavirtual-estorno-dlq --config="retention.ms=-1"

ccloud kafka topic create topic-conn-conf-contavirtual-estorno-retry --partitions 4 --cluster machi-naa
ccloud kafka topic update topic-conn-conf-contavirtual-estorno-retry --config="retention.ms=604800000"


Governados:
topic-conf-contavirtual-estorno
topic-conf-contavirtual-estorno-dlq
topic-conf-contavirtual-estorno-retry

ccloud kafka topic create topic-conf-contavirtual-estorno --partitions 4 --cluster machi-naa
ccloud kafka topic update topic-conf-contavirtual-estorno --config="retention.ms=604800000"

ccloud kafka topic create topic-conf-contavirtual-estorno-dlq --partitions 4 --cluster machi-naa
ccloud kafka topic update topic-conf-contavirtual-estorno-dlq --config="retention.ms=-1"

ccloud kafka topic create topic-conf-contavirtual-estorno-retry --partitions 4 --cluster machi-naa
ccloud kafka topic update topic-conf-contavirtual-estorno-retry --config="retention.ms=604800000"

