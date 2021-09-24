echo "--- Creacion de tablas ---"
aws dynamodb create-table \
    --table-name Planeta \
    --attribute-definitions \
        AttributeName=PlanetaId,AttributeType=S \
    --key-schema \
        AttributeName=PlanetaId,KeyType=HASH \
    --provisioned-throughput \
        ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --region us-east-1 --endpoint http://localhost:8010/

aws dynamodb create-table \
    --table-name Pronostico \
    --attribute-definitions \
        AttributeName=PronosticoId,AttributeType=S \
    --key-schema \
        AttributeName=PronosticoId,KeyType=HASH \
    --provisioned-throughput \
        ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --region us-east-1 --endpoint http://localhost:8010/

echo "--- Poblar tabla planeta ---"
aws dynamodb put-item --table-name Planeta \
--item '{
        "PlanetaId": {"S": "1"},
        "Nombre": {"S": "Ferengi"} ,
        "Distancia": {"N": "500"} ,
        "VelocidadAngular": {"N": "1"},
        "Sentido": {"N": "-1"}
      }' \
--return-consumed-capacity TOTAL \
--region us-east-1 --endpoint http://localhost:8010/

aws dynamodb put-item --table-name Planeta \
--item '{
        "PlanetaId": {"S": "2"},
        "Nombre": {"S": "Betasoide"} ,
        "Distancia": {"N": "2000"} ,
        "VelocidadAngular": {"N": "3"},
        "Sentido": {"N": "-1"}
      }' \
--return-consumed-capacity TOTAL \
--region us-east-1 --endpoint http://localhost:8010/

aws dynamodb put-item --table-name Planeta \
--item '{
        "PlanetaId": {"S": "3"},
        "Nombre": {"S": "Vulcano"} ,
        "Distancia": {"N": "1000"} ,
        "VelocidadAngular": {"N": "5"},
        "Sentido": {"N": "1"}
      }' \
--return-consumed-capacity TOTAL \
--region us-east-1 --endpoint http://localhost:8010/