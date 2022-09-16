comando criar:
ccloud kafka topic create seuTopico --partitions 16 --cluster machi-naa
ccloud kafka topic create topic-conf-contavirtual-estorno --partitions 4 --cluster machi-naa


$$$$$$$$$$$$$$$$$$$$$$$$$$$$
comando atualizar:
ccloud kafka topic update topic-conf-contavirtual-estorno --config="retention.ms=604800000"
ccloud kafka topic create topic-conn-conf-contavirtual-estorno-dlq --partitions 4 --cluster machi-naa


$$$$$$$$$$$$$$$$$$$$$$$$$$$$
action=create
topic=topic-conn-conf-contavirtual-estorno-dlq
replication-factor=1
partitions=4
retention.ms=-1
ccloud kafka topic update topic-conn-conf-contavirtual-estorno-dlq --config="-1"
ccloud kafka topic create topic-conn-conf-contavirtual-estorno-retry --partitions 4 --cluster machi-naa
ccloud kafka topic update topic-conn-conf-contavirtual-estorno-retry --config="604800000"




 ccloud kafka topic describe topic-conf-contavirtual-estorn