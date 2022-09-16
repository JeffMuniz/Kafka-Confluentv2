import pyodbc, sys, re, requests, os, time
from pathlib import Path

#EXTERNAL VARIABLES
print("###############################################################")
print("[EXTERNAL VARIABLE] [ADDING]")
backupDate=sys.argv[1] if sys.argv[1] else '1970-01-01'
prefixConnectorName = sys.argv[2] if sys.argv[2] else 'conn-'
deleteConnector=sys.argv[3] if sys.argv[3] else 'y'
runRestore=sys.argv[4] if sys.argv[4] else 'y'
addConnector=sys.argv[5] if sys.argv[5] else 'y'
path=sys.argv[6] if sys.argv[6] else ''

print("Didn't forget to add external variable (Not required);)")
print("sample: python3 py_script/update_data_connect.py 2021-07-15 conn-in-agendamento y N y ''")

print("backupDate ---------------> " + backupDate)
print("prefixConnectorName ------> " + prefixConnectorName)
print("deleteConnector ----------> " + deleteConnector)
print("runRestore ---------------> " + runRestore)
print("addConnector -------------> " + addConnector)
print("path ---------------------> " + path)

print("python_command (required)|py_file (required)               |backup_date (1970-01-01) |prefix_connector_name (conn-)  |delete_connector (y) |run_restore (y)      |add_connector (y)    |path (/..)                          |")
print("-------------------------|---------------------------------|-------------------------|-------------------------------|---------------------|---------------------|---------------------|------------------------------------|")
print("python3                  |py_script/update_data_connect.py |2021-06-29               |conn-in-info-agendamento-pedido|N                    |N                    |N                    |../git/conta-ajuste/cnn-cnf-extrato |")
print("[EXTERNAL VARIABLE] [ADDED]")
print("###############################################################")
print("")

#CONSTANT VARIABLES
print("")
print("###############################################################")
print("[CONSTANT VARIABLES] [ADDING]")
restConnectUrl='172.31.55.92'
server = '10.70.30.26,1433' 
database = 'blue_qa'
username = 'UserPier' 
passingthroughportalword = 'UserPier'
entries = Path(path)
strConnection='DRIVER={ODBC Driver 17 for SQL Server};SERVER='+server+';DATABASE='+database+';UID='+username+';PWD='+ passingthroughportalword
print("[CONSTANTS VARIABLE] [ADDED]")
print("###############################################################")
print("")

#DELETE CONNECTOR
if deleteConnector == 'y':
    print("")
    startDeleteConnectors = time.perf_counter()
    for entry in entries.iterdir():
        if prefixConnectorName in entry.name:
            print("###############################################################")
            print("[CONFLUENT] [REQUEST] [DELETING] [CONNECTORS] ["+ entry.name + "]")
            url = 'http://'+ restConnectUrl +':8083/connectors/'+ entry.name.replace(".json", "")
            headers = {'content-type': 'application/json'}
            response = requests.delete(url, headers=headers)
            print(response)
            if response.status_code == 204:
                print("[DELETED CONNECTOR]")
            else:
                print("[DIDNT DELETE CONNECTOR]")
                #sys.exit()
            print("[CONFLUENT] [REQUEST] [DELETED] [CONNECTORS] ["+ entry.name + "]")
            print("###############################################################")
            print("")
    endtDeleteConnectors = time.perf_counter()
    print(f"[CONFLUENT] [REQUEST] [DELETED] [CONNECTORS] [TIME]: {endtDeleteConnectors - startDeleteConnectors:0.4}"  )


#DATABASE
if runRestore == 'y':
    print("")
    startDeleteProc = time.perf_counter()
    print("###############################################################")
    print("[DATABASE] [SQLSERVER] [CONNECTING..]")
    cnxn = pyodbc.connect(strConnection, autocommit=True)
    #cnxn.autocommit = True
    print("[DATABASE] [SQLSERVER] [CONNECTED]")
    print("###############################################################")

    print("")

    print("###############################################################")
    print("[SQLSERVER] [EXECUTING DML] [DROP DATABSE] [BLUE_FNC_STG]")
    cursor = cnxn.cursor()
    cursor.execute("USE [msdb] ALTER DATABASE [BLUE_FNC_STG] SET SINGLE_USER WITH ROLLBACK IMMEDIATE; ")
    cursor.execute("USE [msdb] DROP DATABASE [BLUE_FNC_STG];")
    print("[SQLSERVER] [EXECUTED DML] [DROP DATABSE] [BLUE_FNC_STG]")
    print("###############################################################")

    print("")

    print("###############################################################")
    print("[SQLSERVER] [EXECUTING DML] [BACKUP/RESTORE DATABSE] [BLUE_FNC_STG]")
    params=('Blue','BLUE_FNC_STG','BLUEDB',backupDate,1,1)
    cursor = cnxn.cursor()
    cursor.execute("USE [msdb] SET NOCOUNT ON EXECUTE [dbo].[SPR_RestoreDatabase] @database_name=?,@new_database_name = ?,@container_name = ?,@backup_date = ?,@move = ?,@execute = ?;", params)
    while cursor.nextset():
        #time.sleep(5)
        pass

    cursor.close()
    cnxn.close()
    endtDeleteProc = time.perf_counter()
    print(f"[SQLSERVER] [EXECUTED DML] [BACKUP/RESTORE DATABSE] [BLUE_FNC_STG] [TIME]: {endtDeleteProc - startDeleteProc:0.4}"  )
    print("[SQLSERVER] [EXECUTED DML] [BACKUP/RESTORE DATABSE] [BLUE_FNC_STG]")
    print("###############################################################")

    print("")

#ADD CONNECTOR
if addConnector == 'y':
    print("")
    startAddConnectors = time.perf_counter()
    for entry in entries.iterdir():
        if prefixConnectorName in entry.name:
            print("###############################################################")
            print("[CONFLUENT] [REQUEST] [ADDING] [CONNECTORS] ["+ entry.name + "]")
            openFile = open(entry, 'rt')
            readFile = openFile.read()
            replaceFile = readFile\
                .replace("{{ENV_DB_HOST}}", os.environ['STG_CCLOUD_DB_HOST'])\
                .replace("{{ENV_DB_PORT}}", os.environ['STG_CCLOUD_DB_PORT'])\
                .replace("{{ENV_DB_BLUE_NAME}}", os.environ['STG_CCLOUD_DB_BLUE_NAME'])\
                .replace("{{ENV_DB_ADQ_NAME}}", os.environ['STG_CCLOUD_DB_ADQ_NAME'])\
                .replace("confluent_Machina_login", os.environ['STG_CCLOUD_DB_USER'])\
                .replace("Machina-Pawword", os.environ['STG_CCLOUD_DB_passingthroughportalword'])\
                .replace("https://machina-aws-clusters.us-east-2.aws.confluent.cloud", os.environ['STG_CCLOUD_API_SCHEMA_REGISTRY_URL'])\
                .replace("{{ENV_ELASSANDRA}}", os.environ['STG_CCLOUD_ELASSANDRA'])\
                .replace("{{ENV_KEYSPACE_BI}}", os.environ['STG_CCLOUD_KEYSPACE_BI'])\
                .replace("{{ENV_DB_CAN_ADQ_NAME}}", os.environ['STG_CCLOUD_DB_CAN_ADQ_NAME'])\
                .replace("{{ENV_DB_BLUE_EXTRATO_ELETRONICO}}", os.environ['STG_CCLOUD_DB_BLUE_EXTRATO_ELETRONICO'])\
                .replace("{{ENV_API_SCHEMA_REGISTRY_KEY}}", os.environ['STG_CCLOUD_API_SCHEMA_REGISTRY_KEY'])\
                .replace("{{ENV_API_SCHEMA_REGISTRY_SECRET}}", 'amko+rrm3bURPEzO7/lkbCxlhOdizu5VMuqxkCaDj86zNiRNLoKO069dwO2lIFuw')
            url = 'http://'+ restConnectUrl +':8083/connectors/'
            headers = {'content-type': 'application/json'}
            response = requests.post(url, data=replaceFile, headers=headers)
            print(response)
            if response.status_code == 201:
                print("[ADDED CONNECTOR]")
            else:
                print("[DIDNT ADD CONNECTOR]")
                #sys.exit()
            print("[CONFLUENT] [REQUEST] [ADDED] [CONNECTORS] ["+ entry.name + "]")
            print("###############################################################")
            openFile.close()
    endtAddConnectors = time.perf_counter()
    print(f"[CONFLUENT] [REQUEST] [ADD] [CONNECTORS] [TIME]: {endtAddConnectors - startAddConnectors:0.4}"  )