ccloud kafka topic create topic-conn-conf-contavirtual-estorno --partitions 4 --cluster machi-naa
ccloud kafka topic update topic-conn-conf-contavirtual-estorno --config="retention.ms=604800000"
ccloud kafka topic create topic-conn-conf-contavirtual-estorno-dlq --partitions 4 --cluster machi-naa
ccloud kafka topic update topic-conn-conf-contavirtual-estorno-dlq --config="-1"
ccloud kafka topic update topic-conn-conf-contavirtual-estorno-dlq --config=-1
ccloud kafka topic update topic-conn-conf-contavirtual-estorno-dlq --config="retention.ms=-1"
ccloud kafka topic create topic-conn-conf-contavirtual-estorno-retry --partitions 4 --cluster machi-naa
ccloud kafka topic update topic-conn-conf-contavirtual-estorno-retry --config="604800000"
ccloud kafka topic create topic-conf-contavirtual-estorno-dlq --partitions 4 --cluster machi-naa
ccloud kafka topic update topic-conf-contavirtual-estorno-dlq --config="retention.ms=-1"
ccloud kafka topic create topic-conf-contavirtual-estorno-retry --partitions 4 --cluster machi-naa
ccloud kafka topic update topic-conf-contavirtual-estorno-retry --config="retention.ms=604800000"[ec2-user@ip-172-22-33-216 tmp]$ 
ccloud kafka topic update topic-conf-contavirtual-estorno-retry --config="retention.ms=604800000"
                                                                                       604800000 